package hu.bmrk.bmoneytrackerbackend.controller;

import hu.bmrk.bmoneytrackerbackend.entity.UserEntity;
import hu.bmrk.bmoneytrackerbackend.service.interfaces.UserEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BaseController {

    @Autowired
    UserEntityService userEntityRepository;

    @GetMapping("/{id}")
    public UserEntity getById(@PathVariable Long id){
        return userEntityRepository.findFirstById(id);
    }

}
