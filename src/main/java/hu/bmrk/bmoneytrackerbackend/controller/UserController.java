package hu.bmrk.bmoneytrackerbackend.controller;

import hu.bmrk.bmoneytrackerbackend.entity.DTO.UserEntityDTO;
import hu.bmrk.bmoneytrackerbackend.service.interfaces.UserEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    UserEntityService userEntityService;


    @GetMapping
    public ResponseEntity<UserEntityDTO> getCurrentUser(Authentication authentication){
        return new ResponseEntity<>(userEntityService.findByUsername(authentication.getName()), HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<UserEntityDTO>> getUsers(){

        return new ResponseEntity<>(userEntityService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserEntityDTO> getById(@PathVariable Long id){

        return new ResponseEntity<>(userEntityService.findFirstById(id), HttpStatus.OK);
    }

}
