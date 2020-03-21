package hu.bmrk.bmoneytrackerbackend.controller;

import hu.bmrk.bmoneytrackerbackend.entity.Category;
import hu.bmrk.bmoneytrackerbackend.entity.DTO.CategoryDTO;
import hu.bmrk.bmoneytrackerbackend.entity.UserEntity;
import hu.bmrk.bmoneytrackerbackend.service.interfaces.CategoryService;
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
@RequestMapping("/categories")
@CrossOrigin(origins = "*")
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    HelperUtil helper;

    @GetMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<List<CategoryDTO>> getCategoriesForUser(Authentication authentication){
        List<CategoryDTO> categoryDTOS = new ArrayList<>();
        UserEntity user = helper.getUser(authentication);
        for(Category c : categoryService.findAllByUserEntity_Id(user.getId())){
            categoryDTOS.add(modelMapper.map(c, CategoryDTO.class));
        }
        return new ResponseEntity<>(categoryDTOS, HttpStatus.OK);
    }

    @PostMapping(
            path = "/new",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<CategoryDTO> createCategory(@RequestBody CategoryDTO category) {
        categoryService.saveCategory(modelMapper.map(category, Category.class));
        return new ResponseEntity<>(category, HttpStatus.OK);
    }


}
