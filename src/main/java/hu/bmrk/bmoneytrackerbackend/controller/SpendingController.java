package hu.bmrk.bmoneytrackerbackend.controller;

import hu.bmrk.bmoneytrackerbackend.entity.DTO.SpendingDTO;
import hu.bmrk.bmoneytrackerbackend.util.OwnDateUtil;
import hu.bmrk.bmoneytrackerbackend.entity.Spending;
import hu.bmrk.bmoneytrackerbackend.service.interfaces.SpendingService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/spendings")
public class SpendingController {

    @Autowired
    SpendingService spendingService;

    @Autowired
    ModelMapper modelMapper;

    @GetMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<List<Spending>> getSpendings(@RequestBody Long userId) {
        return new ResponseEntity<>(spendingService.findAllByUserEntity_Id(userId), HttpStatus.OK);
    }

    @GetMapping(
            path = "/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Spending> getSpendingById(@PathVariable Long id, @RequestBody Long userId) {
        return new ResponseEntity<>(spendingService.findFirstByIdAndUserEntity_Id(id, userId), HttpStatus.OK);
    }

    @GetMapping(
            path = "/{userId}/date",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<List<Spending>> getSpedingsBetween(
            @RequestBody OwnDateUtil date,
            @PathVariable Long userId
    ) {
        return new ResponseEntity<>(spendingService.findAllByDateBetweenAndUserEntity_Id(date.getDateFrom(), date.getDateTo(), userId), HttpStatus.OK);
    }

    @PostMapping(
            path = "/new",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Spending> createSpending(@RequestBody SpendingDTO spending) {
        return new ResponseEntity<>(spendingService.saveSpending(modelMapper.map(spending, Spending.class)), HttpStatus.OK);
    }

    @PostMapping(path = "/delete/{id}")
    public void deleteSpending(@PathVariable Long id) {
        spendingService.deleteSpendingById(id);
    }



}
