package hu.bmrk.bmoneytrackerbackend.controller;

import hu.bmrk.bmoneytrackerbackend.entity.DTO.UserEntityDTO;
import hu.bmrk.bmoneytrackerbackend.entity.UserEntity;
import hu.bmrk.bmoneytrackerbackend.service.interfaces.UserEntityService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    UserEntityService userEntityService;

    @Autowired
    ModelMapper modelMapper;


    @GetMapping
    public ResponseEntity<List<UserEntityDTO>> getUsers(){
        List<UserEntityDTO> userEntityDTOS = new ArrayList<>();
        for(UserEntity u: userEntityService.findAll()){
            userEntityDTOS.add(modelMapper.map(u,UserEntityDTO.class));
        }
        return new ResponseEntity<>(userEntityDTOS, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserEntityDTO> getById(@PathVariable Long id){

        return new ResponseEntity<>(modelMapper.map(userEntityService.findFirstById(id), UserEntityDTO.class), HttpStatus.OK);
    }

}
