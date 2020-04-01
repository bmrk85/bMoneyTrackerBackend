package hu.bmrk.bmoneytrackerbackend.controller;


import hu.bmrk.bmoneytrackerbackend.entity.Category;
import hu.bmrk.bmoneytrackerbackend.entity.DTO.IncomeDTO;
import hu.bmrk.bmoneytrackerbackend.entity.Income;
import hu.bmrk.bmoneytrackerbackend.entity.UserEntity;
import hu.bmrk.bmoneytrackerbackend.service.interfaces.CategoryService;
import hu.bmrk.bmoneytrackerbackend.service.interfaces.IncomeService;
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

    @Autowired
    HelperUtil helper;

    @GetMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<List<IncomeDTO>> getIncomesForLoggedInUser(Authentication authentication) {
        UserEntity user = helper.getUser(authentication);
        List<IncomeDTO> incomeDTOS = new ArrayList<>();
        for (Income i : incomeService.findAllByUserEntity_Id(user.getId())) {
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
        UserEntity user = helper.getUser(authentication);

        List<IncomeDTO> incomeDTOS = new ArrayList<>();
        for(Income i : incomeService.findAllByDateBetweenAndUserEntity_Id(dateFrom, dateTo, user.getId())){
            incomeDTOS.add(modelMapper.map(i, IncomeDTO.class));
        }
        return new ResponseEntity<>(incomeDTOS, HttpStatus.OK);
    }

    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<IncomeDTO> createIncome(Authentication authentication, @RequestBody IncomeDTO incomeDTO) {
        UserEntity user = helper.getUser(authentication);
        Income income = modelMapper.map(incomeDTO, Income.class);
        income.setUserEntity(user);
        if(income.getCategory().getId() == null){
            income.getCategory().setUserEntity(user);
            income.setCategory(categoryService.saveCategory(modelMapper.map(income.getCategory(), Category.class)));
        }
        incomeService.saveIncome(modelMapper.map(income, Income.class));
        return new ResponseEntity<>(modelMapper.map(income, IncomeDTO.class), HttpStatus.OK);
    }

    @PostMapping(path = "/delete/{id}")
    public void deleteIncome(@PathVariable Long id) {
        incomeService.deleteIncomeById(id);
    }

}
