package hu.bmrk.bmoneytrackerbackend.controller;

import hu.bmrk.bmoneytrackerbackend.entity.DTO.CashFlowDTO;
import hu.bmrk.bmoneytrackerbackend.entity.Income;
import hu.bmrk.bmoneytrackerbackend.entity.Spending;
import hu.bmrk.bmoneytrackerbackend.entity.UserEntity;
import hu.bmrk.bmoneytrackerbackend.service.interfaces.CategoryService;
import hu.bmrk.bmoneytrackerbackend.service.interfaces.IncomeService;
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
@RequestMapping("/cashflow")
@CrossOrigin(origins = "*")
public class CashFlowController {

    @Autowired
    IncomeService incomeService;

    @Autowired
    UserEntityService userEntityService;

    @Autowired
    CategoryService categoryService;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    SpendingService spendingService;

    @Autowired
    HelperUtil helper;

    @GetMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<List<CashFlowDTO>> getCashFlowForLoggedInUser(
            @RequestParam("dateFrom") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date dateFrom,
            @RequestParam("dateTo") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date dateTo,
            Authentication authentication
    ) {
        UserEntity user = helper.getUser(authentication);
        List<CashFlowDTO> cashFlowDTOS = new ArrayList<>();
        for (Income i : incomeService.findAllByDateBetweenAndUserEntity_Id(dateFrom,dateTo, user.getId())) {
            cashFlowDTOS.add(modelMapper.map(i,CashFlowDTO.class));
        }
        for(Spending s: spendingService.findAllByDateBetweenAndUserEntity_Id(dateFrom,dateTo, user.getId())){
            cashFlowDTOS.add(modelMapper.map(s,CashFlowDTO.class));
        }
        return new ResponseEntity<>(cashFlowDTOS, HttpStatus.OK);
    }


}
