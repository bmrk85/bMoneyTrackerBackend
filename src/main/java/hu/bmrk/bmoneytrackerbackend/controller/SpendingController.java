package hu.bmrk.bmoneytrackerbackend.controller;

import hu.bmrk.bmoneytrackerbackend.entity.DTO.SpendingDTO;
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
    public ResponseEntity<List<SpendingDTO>> getSpendingsForUser(Authentication authentication) {

        return new ResponseEntity<>(spendingService.findAllByUserEntity_IdOrderByDateFromAsc(helper.getUser(authentication).getId()), HttpStatus.OK);
    }

    @GetMapping(
            path = "/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<SpendingDTO> getSpendingById(@PathVariable Long id, @RequestParam Long userId) {
        return new ResponseEntity<>(spendingService.findFirstByIdAndUserEntity_Id(id, userId), HttpStatus.OK);
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
        return new ResponseEntity<>(spendingService.findAllByDateBetweenAndUserEntity_Id(
                dateFrom,dateTo,helper.getUser(authentication).getId()
        ), HttpStatus.OK);
    }

    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<SpendingDTO> createSpending(Authentication authentication, @RequestBody SpendingDTO spendingDTO) {

        return new ResponseEntity<>(spendingService.save(spendingDTO, helper.getUser(authentication)), HttpStatus.OK);
    }

    @PostMapping(path = "/delete/{id}")
    public void deleteSpending(@PathVariable Long id) {
        spendingService.deleteById(id);
    }



}
