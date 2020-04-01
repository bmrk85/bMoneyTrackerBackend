package hu.bmrk.bmoneytrackerbackend.controller;

import hu.bmrk.bmoneytrackerbackend.entity.Category;
import hu.bmrk.bmoneytrackerbackend.entity.DTO.SpendingDTO;
import hu.bmrk.bmoneytrackerbackend.entity.Spending;
import hu.bmrk.bmoneytrackerbackend.entity.UserEntity;
import hu.bmrk.bmoneytrackerbackend.service.interfaces.CategoryService;
import hu.bmrk.bmoneytrackerbackend.service.interfaces.SpendingService;
import hu.bmrk.bmoneytrackerbackend.service.interfaces.UserEntityService;
import hu.bmrk.bmoneytrackerbackend.util.HelperUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

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
    HelperUtil helper;


    @GetMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<List<SpendingDTO>> getSpendingsForLoggedInUser(Authentication authentication) {
        UserEntity user = helper.getUser(authentication);
        List<SpendingDTO> spendingDTOS = new ArrayList<>();
        for (Spending s : spendingService.findAllByUserEntity_Id(user.getId())) {
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
            Authentication authentication
    ) {
        UserEntity user = helper.getUser(authentication);

        List<SpendingDTO> spendingDTOS = new ArrayList<>();
        for(Spending s : spendingService.findAllByDateBetweenAndUserEntity_Id(dateFrom, dateTo, user.getId())){
            spendingDTOS.add(modelMapper.map(s, SpendingDTO.class));
        }
        return new ResponseEntity<>(spendingDTOS, HttpStatus.OK);
    }

    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<SpendingDTO> createSpending(Authentication authentication, @RequestBody SpendingDTO spendingDTO) {
        UserEntity user = helper.getUser(authentication);
        Spending spending = modelMapper.map(spendingDTO, Spending.class);
        spending.setUserEntity(user);

        if(spending.getCategory().getId() == null){
            spending.getCategory().setUserEntity(user);
            spending.setCategory(categoryService.saveCategory(modelMapper.map(spending.getCategory(), Category.class)));
        }
        return new ResponseEntity<>(modelMapper.map(spendingService.saveSpending(spending), SpendingDTO.class), HttpStatus.OK);
    }

    @PostMapping(path = "/delete/{id}")
    public void deleteSpending(@PathVariable Long id) {
        spendingService.deleteSpendingById(id);
    }



}
