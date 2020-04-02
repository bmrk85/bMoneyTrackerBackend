package hu.bmrk.bmoneytrackerbackend.controller;

import hu.bmrk.bmoneytrackerbackend.entity.DTO.SavingDTO;
import hu.bmrk.bmoneytrackerbackend.service.interfaces.SavingService;
import hu.bmrk.bmoneytrackerbackend.util.HelperUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/savings")
@CrossOrigin(origins = "*")
public class SavingController {

    @Autowired
    SavingService savingService;

    @Autowired
    HelperUtil helper;

    @GetMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<List<SavingDTO>> getSavingsForUser(Authentication authentication) {
        return new ResponseEntity<>(savingService.findAllByUserEntity_IdOrderByDateFromAsc(helper.getUser(authentication).getId()), HttpStatus.OK);
    }

    @GetMapping(
            path = "/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<SavingDTO> getSavingById(@PathVariable Long id, Authentication authentication) {
        return new ResponseEntity<>(savingService.findFirstByIdAndUserEntity_Id(id, helper.getUser(authentication).getId()), HttpStatus.OK);
    }

    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<SavingDTO> createSaving(@RequestBody SavingDTO savingDTO, Authentication authentication) {

        return new ResponseEntity<>(savingService.save(savingDTO, helper.getUser(authentication)), HttpStatus.OK);
    }

    @PostMapping(path = "/delete/{id}")
    public void deleteSaving(@PathVariable Long id) {
        savingService.deleteById(id);
    }
}
