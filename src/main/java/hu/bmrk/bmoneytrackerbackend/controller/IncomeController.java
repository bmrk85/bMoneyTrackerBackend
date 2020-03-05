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

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/incomes")
@CrossOrigin(origins = "*")
public class IncomeController {

    @Autowired
    IncomeService incomeService;

    @Autowired
    ModelMapper modelMapper;

    @GetMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<List<IncomeDTO>> getIncomes(@RequestBody Long userId) {
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
    public ResponseEntity<IncomeDTO> getIncomeById(@PathVariable Long id, @RequestBody Long userId) {
        return new ResponseEntity<>(modelMapper.map(incomeService.findFirstByIdAndUserEntity_Id(id, userId), IncomeDTO.class), HttpStatus.OK);
    }

    @GetMapping(
            path = "/{userId}/date",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<List<IncomeDTO>> getSpedingsBetween(
            @RequestBody OwnDateUtil date,
            @PathVariable Long userId
    ) {
        List<IncomeDTO> incomeDTOS = new ArrayList<>();
        for(Income i : incomeService.findAllByDateBetweenAndUserEntity_Id(date.getDateFrom(), date.getDateTo(), userId)){
            incomeDTOS.add(modelMapper.map(i,IncomeDTO.class));
        }
        return new ResponseEntity<>(incomeDTOS, HttpStatus.OK);
    }

    @PostMapping(
            path = "/new",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<IncomeDTO> createIncome(@RequestBody IncomeDTO income) {
        incomeService.saveIncome(modelMapper.map(income, Income.class));
        return new ResponseEntity<>(income, HttpStatus.OK);
    }

    @PostMapping(path = "/delete/{id}")
    public void deleteIncome(@PathVariable Long id) {
        incomeService.deleteIncomeById(id);
    }

}
