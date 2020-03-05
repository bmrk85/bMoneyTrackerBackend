package hu.bmrk.bmoneytrackerbackend.controller;

import hu.bmrk.bmoneytrackerbackend.entity.DTO.SpendingDTO;
import hu.bmrk.bmoneytrackerbackend.entity.Spending;
import hu.bmrk.bmoneytrackerbackend.service.interfaces.SpendingService;
import hu.bmrk.bmoneytrackerbackend.util.JwtTokenUtil;
import hu.bmrk.bmoneytrackerbackend.util.OwnDateUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/spendings")
@CrossOrigin(origins = "*")
public class SpendingController {

    @Autowired
    SpendingService spendingService;

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
            path = "/{userId}/date",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<List<SpendingDTO>> getSpedingsBetween(
            @RequestParam OwnDateUtil date,
            @PathVariable Long userId
    ) {
        List<SpendingDTO> spendingDTOS = new ArrayList<>();
        for(Spending s : spendingService.findAllByDateBetweenAndUserEntity_Id(date.getDateFrom(), date.getDateTo(), userId)){
            spendingDTOS.add(modelMapper.map(s, SpendingDTO.class));
        }
        return new ResponseEntity<>(spendingDTOS, HttpStatus.OK);
    }

    @PostMapping(
            path = "/new",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<SpendingDTO> createSpending(@RequestBody SpendingDTO spending) {
        spendingService.saveSpending(modelMapper.map(spending, Spending.class));
        return new ResponseEntity<>(spending, HttpStatus.OK);
    }

    @PostMapping(path = "/delete/{id}")
    public void deleteSpending(@PathVariable Long id) {
        spendingService.deleteSpendingById(id);
    }



}
