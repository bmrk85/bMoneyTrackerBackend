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

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/savings")
@CrossOrigin(origins = "*")
public class SavingController {

    @Autowired
    SavingService savingService;

    @Autowired
    ModelMapper modelMapper;

    @GetMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<List<SavingDTO>> getSavings(@RequestBody Long userId) {
        List<SavingDTO> savingDTOS = new ArrayList<>();
        for (Saving s : savingService.findAllByUserEntity_Id(userId)) {
            savingDTOS.add(modelMapper.map(s, SavingDTO.class));
        }
        return new ResponseEntity<>(savingDTOS, HttpStatus.OK);
    }

    @GetMapping(
            path = "/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<SavingDTO> getSavingById(@PathVariable Long id, @RequestBody Long userId) {
        return new ResponseEntity<>(modelMapper.map(
                savingService.findFirstByIdAndUserEntity_Id(id, userId), SavingDTO.class
        ), HttpStatus.OK);
    }

    @PostMapping(
            path = "/new",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<SavingDTO> createSaving(@RequestBody SavingDTO saving) {
        savingService.saveSaving(modelMapper.map(saving, Saving.class));
        return new ResponseEntity<>(saving, HttpStatus.OK);
    }

    @PostMapping(path = "/delete/{id}")
    public void deleteSaving(@PathVariable Long id) {
        savingService.deleteSavingById(id);
    }
}
