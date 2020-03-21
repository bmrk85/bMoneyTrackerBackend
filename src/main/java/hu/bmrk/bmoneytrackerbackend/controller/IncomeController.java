package hu.bmrk.bmoneytrackerbackend.controller;


import hu.bmrk.bmoneytrackerbackend.entity.DTO.IncomeDTO;
import hu.bmrk.bmoneytrackerbackend.entity.DTO.UserEntityDTO;
import hu.bmrk.bmoneytrackerbackend.entity.Income;
import hu.bmrk.bmoneytrackerbackend.service.interfaces.CategoryService;
import hu.bmrk.bmoneytrackerbackend.service.interfaces.IncomeService;
import hu.bmrk.bmoneytrackerbackend.service.interfaces.UserEntityService;
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
@RequestMapping("/incomes")
@CrossOrigin(origins = "*")
public class IncomeController {

    @Autowired
    IncomeService incomeService;

    @Autowired
    UserEntityService userEntityService;

    @Autowired
    CategoryService categoryService;

    @Autowired
    ModelMapper modelMapper;

    @GetMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<List<IncomeDTO>> getIncomesForLoggedInUser(Authentication authentication) {
        Long userId = userEntityService.findByUsername(authentication.getName()).getId();
        List<IncomeDTO> incomeDTOS = new ArrayList<>();
        for (Income i : incomeService.findAllByUserEntity_Id(userId)) {
            incomeDTOS.add(modelMapper.map(i, IncomeDTO.class));
        }
        return new ResponseEntity<>(incomeDTOS, HttpStatus.OK);
    }

    @GetMapping(
            path = "/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<IncomeDTO> getIncomeById(@PathVariable Long id, @RequestParam Long userId) {
        return new ResponseEntity<>(modelMapper.map(
                incomeService.findFirstByIdAndUserEntity_Id(id, userId),IncomeDTO.class
        ), HttpStatus.OK);
    }

    @GetMapping(
            path = "/date",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<List<IncomeDTO>> getIncomesBetween(
            @RequestParam("dateFrom") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date dateFrom,
            @RequestParam("dateTo") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date dateTo,
            Authentication authentication
    ) {
        Long userId = userEntityService.findByUsername(authentication.getName()).getId();

        List<IncomeDTO> incomeDTOS = new ArrayList<>();
        for(Income i : incomeService.findAllByDateBetweenAndUserEntity_Id(dateFrom, dateTo, userId)){
            incomeDTOS.add(modelMapper.map(i, IncomeDTO.class));
        }
        return new ResponseEntity<>(incomeDTOS, HttpStatus.OK);
    }

    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<IncomeDTO> createIncome(Authentication authentication, @RequestBody IncomeDTO income) {
        Long userId = userEntityService.findByUsername(authentication.getName()).getId();
        income.setUserEntity(modelMapper.map(userEntityService.findFirstById(userId), UserEntityDTO.class));
        incomeService.saveIncome(modelMapper.map(income, Income.class));
        return new ResponseEntity<>(income, HttpStatus.OK);
    }

    @PostMapping(path = "/delete/{id}")
    public void deleteIncome(@PathVariable Long id) {
        incomeService.deleteIncomeById(id);
    }

}
