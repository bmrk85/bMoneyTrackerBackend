package hu.bmrk.bmoneytrackerbackend.controller;

import hu.bmrk.bmoneytrackerbackend.entity.Category;
import hu.bmrk.bmoneytrackerbackend.entity.DTO.CategoryDTO;
import hu.bmrk.bmoneytrackerbackend.service.interfaces.CategoryService;
import hu.bmrk.bmoneytrackerbackend.util.HelperUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

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

        return new ResponseEntity<>(categoryService.getCategoriesForUser(helper.getUser(authentication)), HttpStatus.OK);
    }


    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<CategoryDTO> editCategory(@RequestBody CategoryDTO categoryDTO, Authentication authentication){
        Category category = modelMapper.map(categoryDTO, Category.class);
        category.setUserEntity(helper.getUser(authentication));
        return new ResponseEntity<>(modelMapper.map(categoryService.saveCategory(category),CategoryDTO.class), HttpStatus.OK);
    }


}
