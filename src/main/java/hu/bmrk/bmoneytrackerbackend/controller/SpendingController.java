package hu.bmrk.bmoneytrackerbackend.controller;

import hu.bmrk.bmoneytrackerbackend.entity.DTO.SpendingDTO;
import hu.bmrk.bmoneytrackerbackend.entity.DTO.UserEntityDTO;
import hu.bmrk.bmoneytrackerbackend.entity.Spending;
import hu.bmrk.bmoneytrackerbackend.service.interfaces.CategoryService;
import hu.bmrk.bmoneytrackerbackend.service.interfaces.SpendingService;
import hu.bmrk.bmoneytrackerbackend.service.interfaces.UserEntityService;
import hu.bmrk.bmoneytrackerbackend.util.JwtTokenUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/spendings")
@CrossOrigin(origins = "*")
public class SpendingController {

    @Autowired
    SpendingService spendingService;

    @Autowired
    UserEntityService userEntityService;

    @Autowired
    CategoryService categoryService;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    JwtTokenUtil jwtTokenUtil;

    @GetMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<List<SpendingDTO>> getSpendingsForLoggedInUser(@RequestHeader("Authorization") String token) {
        Long userId = jwtTokenUtil.getIdFromToken(token);
        List<SpendingDTO> spendingDTOS = new ArrayList<>();
        for (Spending s : spendingService.findAllByUserEntity_Id(userId)) {
            spendingDTOS.add(modelMapper.map(s, SpendingDTO.class));
        }
        return new ResponseEntity<>(spendingDTOS, HttpStatus.OK);
    }

    @GetMapping(
            path = "/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<SpendingDTO> getSpendingById(@PathVariable Long id, @RequestParam Long userId) {
        return new ResponseEntity<>(modelMapper.map(
                spendingService.findFirstByIdAndUserEntity_Id(id, userId),SpendingDTO.class
        ), HttpStatus.OK);
    }

    @GetMapping(
            path = "/date",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<List<SpendingDTO>> getSpedingsBetween(
            @RequestParam("dateFrom") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date dateFrom,
            @RequestParam("dateTo") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date dateTo,
            @RequestHeader("Authorization") String token
    ) {
        Long userId = jwtTokenUtil.getIdFromToken(token);

        List<SpendingDTO> spendingDTOS = new ArrayList<>();
        for(Spending s : spendingService.findAllByDateBetweenAndUserEntity_Id(new Timestamp(dateFrom.getTime()), new Timestamp(dateTo.getTime()), userId)){
            spendingDTOS.add(modelMapper.map(s, SpendingDTO.class));
        }
        return new ResponseEntity<>(spendingDTOS, HttpStatus.OK);
    }

    @PostMapping(
            path = "/new",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<SpendingDTO> createSpending(@RequestHeader("Authorization") String token, @RequestBody SpendingDTO spending) {
        Long userId = jwtTokenUtil.getIdFromToken(token);
        spending.setUserEntity(modelMapper.map(userEntityService.findFirstById(userId), UserEntityDTO.class));
        spendingService.saveSpending(modelMapper.map(spending, Spending.class));
        return new ResponseEntity<>(spending, HttpStatus.OK);
    }

    @PutMapping(
            path = "/edit",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<SpendingDTO> updateSpending(@RequestHeader("Authorization") String token, @RequestBody SpendingDTO spending) {
        Long userId = jwtTokenUtil.getIdFromToken(token);
        Spending tmp = spendingService.findFirstByIdAndUserEntity_Id(spending.getId(),userId);
        if(tmp != null){
            spending.setId(tmp.getId());
        }
        spendingService.saveSpending(modelMapper.map(spending, Spending.class));
        return new ResponseEntity<>(spending, HttpStatus.OK);
    }


    @PostMapping(path = "/delete/{id}")
    public void deleteSpending(@PathVariable Long id) {
        spendingService.deleteSpendingById(id);
    }



}
