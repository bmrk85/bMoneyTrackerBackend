package hu.bmrk.bmoneytrackerbackend.util;

import hu.bmrk.bmoneytrackerbackend.entity.Category;
import hu.bmrk.bmoneytrackerbackend.entity.DTO.CategoryDTO;
import hu.bmrk.bmoneytrackerbackend.entity.DTO.UserEntityDTO;
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

    public void checkCategoryForUser(CategoryDTO category, UserEntityDTO user) {
        if (categoryService.findCategoryByTitleAndUserEntity_Id(category.getTitle(), user.getId()) == null) {
            category.setUserEntity(user);
            categoryService.saveCategory(modelMapper.map(category, Category.class));
        }
    }

    public UserEntity getUser(Authentication authentication){
        return userEntityService.findByUsername(authentication.getName());
    }

}
