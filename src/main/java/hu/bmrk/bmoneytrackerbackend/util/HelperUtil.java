package hu.bmrk.bmoneytrackerbackend.util;

import hu.bmrk.bmoneytrackerbackend.entity.UserEntity;
import hu.bmrk.bmoneytrackerbackend.service.interfaces.CategoryService;
import hu.bmrk.bmoneytrackerbackend.service.interfaces.UserEntityService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class HelperUtil {

    @Autowired
    CategoryService categoryService;

    @Autowired
    UserEntityService userEntityService;

    @Autowired
    ModelMapper modelMapper;


    public <D, T> D map(final T entity, Class<D> outClass){
        return modelMapper.map(entity, outClass);
    }

    public <D, T> List<D> mapAll(final Collection<T> entityList, Class<D> outCLass) {
        return entityList.stream()
                .map(entity -> map(entity, outCLass))
                .collect(Collectors.toList());
    }

    public UserEntity getUser(Authentication authentication) {
        return map(userEntityService.findByUsername(authentication.getName()), UserEntity.class);
    }

}
