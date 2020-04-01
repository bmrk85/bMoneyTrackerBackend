package hu.bmrk.bmoneytrackerbackend.controller;

import hu.bmrk.bmoneytrackerbackend.entity.Category;
import hu.bmrk.bmoneytrackerbackend.entity.DTO.SavingDTO;
import hu.bmrk.bmoneytrackerbackend.entity.Saving;
import hu.bmrk.bmoneytrackerbackend.entity.UserEntity;
import hu.bmrk.bmoneytrackerbackend.service.interfaces.CategoryService;
import hu.bmrk.bmoneytrackerbackend.service.interfaces.SavingService;
import hu.bmrk.bmoneytrackerbackend.service.interfaces.UserEntityService;
import hu.bmrk.bmoneytrackerbackend.util.HelperUtil;
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

    @Autowired
    CategoryService categoryService;

    @Autowired
    HelperUtil helper;

    @GetMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<List<SavingDTO>> getSavings(Authentication authentication) {
        List<SavingDTO> savingDTOS = new ArrayList<>();
        UserEntity user = helper.getUser(authentication);
        for (Saving s : savingService.findAllByUserEntity_Id(user.getId())) {
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
                savingService.findFirstByIdAndUserEntity_Id(id, helper.getUser(authentication).getId()), SavingDTO.class
        ), HttpStatus.OK);
    }

    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<SavingDTO> createSaving(@RequestBody SavingDTO savingDTO, Authentication authentication) {
        UserEntity user = helper.getUser(authentication);
        Saving saving = modelMapper.map(savingDTO, Saving.class);
        saving.setUserEntity(user);
        if(saving.getCategory().getId() == null){
            saving.getCategory().setUserEntity(user);
            saving.setCategory(categoryService.saveCategory(modelMapper.map(saving.getCategory(), Category.class)));
        }
        savingService.saveSaving(modelMapper.map(saving, Saving.class));
        return new ResponseEntity<>(modelMapper.map(saving, SavingDTO.class), HttpStatus.OK);
    }

    @PostMapping(path = "/delete/{id}")
    public void deleteSaving(@PathVariable Long id) {
        savingService.deleteSavingById(id);
    }
}
