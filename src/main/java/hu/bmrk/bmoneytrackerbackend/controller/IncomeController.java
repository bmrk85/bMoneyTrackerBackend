package hu.bmrk.bmoneytrackerbackend.controller;


import hu.bmrk.bmoneytrackerbackend.entity.DTO.IncomeDTO;
import hu.bmrk.bmoneytrackerbackend.service.interfaces.IncomeService;
import hu.bmrk.bmoneytrackerbackend.util.HelperUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/incomes")
@CrossOrigin(origins = "*")
public class IncomeController {

    @Autowired
    IncomeService incomeService;

    @Autowired
    HelperUtil helper;

    @GetMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<List<IncomeDTO>> getIncomesForUser(Authentication authentication) {
        return new ResponseEntity<>(incomeService.findAllByUserEntity_IdOrderByDateFromAsc(helper.getUser(authentication).getId()), HttpStatus.OK);
    }

    @GetMapping(
            path = "/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<IncomeDTO> getIncomeById(@PathVariable Long id, @RequestParam Long userId) {
        return new ResponseEntity<>(incomeService.findFirstByIdAndUserEntity_Id(id, userId), HttpStatus.OK);
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
        return new ResponseEntity<>(incomeService.findAllByDateBetweenAndUserEntity_Id(
                dateFrom,dateTo,helper.getUser(authentication).getId()
        ), HttpStatus.OK);
    }

    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<IncomeDTO> createIncome(Authentication authentication, @RequestBody IncomeDTO incomeDTO) {

        return new ResponseEntity<>(incomeService.save(incomeDTO, helper.getUser(authentication)), HttpStatus.OK);
    }

    @PostMapping(path = "/delete/{id}")
    public void deleteIncome(@PathVariable Long id) {
        incomeService.deleteById(id);
    }

}
