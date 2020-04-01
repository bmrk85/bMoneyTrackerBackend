package hu.bmrk.bmoneytrackerbackend.util;

import hu.bmrk.bmoneytrackerbackend.entity.UserEntity;
import hu.bmrk.bmoneytrackerbackend.service.interfaces.CategoryService;
import hu.bmrk.bmoneytrackerbackend.service.interfaces.UserEntityService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class HelperUtil {

    @Autowired
    CategoryService categoryService;

    @Autowired
    UserEntityService userEntityService;

    @Autowired
    ModelMapper modelMapper;


    public UserEntity getUser(Authentication authentication) {
        return userEntityService.findByUsername(authentication.getName());
    }

}
