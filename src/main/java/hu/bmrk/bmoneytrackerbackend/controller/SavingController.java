package hu.bmrk.bmoneytrackerbackend.controller;

import hu.bmrk.bmoneytrackerbackend.entity.DTO.SavingDTO;
import hu.bmrk.bmoneytrackerbackend.entity.Saving;
import hu.bmrk.bmoneytrackerbackend.service.interfaces.SavingService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/savings")
public class SavingController {

    @Autowired
    SavingService savingService;

    @Autowired
    ModelMapper modelMapper;

    @GetMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<List<Saving>> getSavings(@RequestBody Long userId) {
        return new ResponseEntity<>(savingService.findAllByUserEntity_Id(userId), HttpStatus.OK);
    }

    @GetMapping(
            path = "/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Saving> getSavingById(@PathVariable Long id, @RequestBody Long userId) {
        return new ResponseEntity<>(savingService.findFirstByIdAndUserEntity_Id(id, userId), HttpStatus.OK);
    }

    @PostMapping(
            path = "/new",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Saving> createSaving(@RequestBody SavingDTO saving) {
        return new ResponseEntity<>(savingService.saveSaving(modelMapper.map(saving, Saving.class)), HttpStatus.OK);
    }

    @PostMapping(path = "/delete/{id}")
    public void deleteSaving(@PathVariable Long id) {
        savingService.deleteSavingById(id);
    }
}
