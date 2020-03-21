package hu.bmrk.bmoneytrackerbackend.controller;

import hu.bmrk.bmoneytrackerbackend.entity.DTO.SavingDTO;
import hu.bmrk.bmoneytrackerbackend.entity.DTO.UserEntityDTO;
import hu.bmrk.bmoneytrackerbackend.entity.Saving;
import hu.bmrk.bmoneytrackerbackend.service.interfaces.SavingService;
import hu.bmrk.bmoneytrackerbackend.service.interfaces.UserEntityService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
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

    @Autowired
    UserEntityService userEntityService;

    @GetMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<List<SavingDTO>> getSavings(Authentication authentication) {
        List<SavingDTO> savingDTOS = new ArrayList<>();
        Long userId = userEntityService.findByUsername(authentication.getName()).getId();
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
    public ResponseEntity<SavingDTO> getSavingById(@PathVariable Long id, Authentication authentication) {
        return new ResponseEntity<>(modelMapper.map(
                savingService.findFirstByIdAndUserEntity_Id(id, userEntityService.findByUsername(authentication.getName()).getId()), SavingDTO.class
        ), HttpStatus.OK);
    }

    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<SavingDTO> createSaving(@RequestBody SavingDTO saving, Authentication authentication) {
        saving.setUserEntity(modelMapper.map(userEntityService.findByUsername(authentication.getName()), UserEntityDTO.class));
        savingService.saveSaving(modelMapper.map(saving, Saving.class));
        return new ResponseEntity<>(saving, HttpStatus.OK);
    }

    @PostMapping(path = "/delete/{id}")
    public void deleteSaving(@PathVariable Long id) {
        savingService.deleteSavingById(id);
    }
}
