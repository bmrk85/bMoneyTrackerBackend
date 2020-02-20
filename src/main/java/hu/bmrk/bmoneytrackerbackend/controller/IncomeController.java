package hu.bmrk.bmoneytrackerbackend.controller;


import hu.bmrk.bmoneytrackerbackend.entity.DTO.IncomeDTO;
import hu.bmrk.bmoneytrackerbackend.entity.Income;
import hu.bmrk.bmoneytrackerbackend.service.interfaces.IncomeService;
import hu.bmrk.bmoneytrackerbackend.util.OwnDateUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/incomes")
public class IncomeController {

    @Autowired
    IncomeService incomeService;

    @Autowired
    ModelMapper modelMapper;

    @GetMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<List<Income>> getIncomes(@RequestBody Long userId) {
        return new ResponseEntity<>(incomeService.findAllByUserEntity_Id(userId), HttpStatus.OK);
    }

    @GetMapping(
            path = "/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Income> getIncomeById(@PathVariable Long id, @RequestBody Long userId) {
        return new ResponseEntity<>(incomeService.findFirstByIdAndUserEntity_Id(id, userId), HttpStatus.OK);
    }

    @GetMapping(
            path = "/{userId}/date",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<List<Income>> getSpedingsBetween(
            @RequestBody OwnDateUtil date,
            @PathVariable Long userId
    ) {
        return new ResponseEntity<>(incomeService.findAllByDateBetweenAndUserEntity_Id(date.getDateFrom(), date.getDateTo(), userId), HttpStatus.OK);
    }

    @PostMapping(
            path = "/new",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Income> createIncome(@RequestBody IncomeDTO income) {
        return new ResponseEntity<>(incomeService.saveIncome(modelMapper.map(income, Income.class)), HttpStatus.OK);
    }

    @PostMapping(path = "/delete/{id}")
    public void deleteIncome(@PathVariable Long id) {
        incomeService.deleteIncomeById(id);
    }

}
