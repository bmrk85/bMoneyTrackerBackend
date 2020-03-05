package hu.bmrk.bmoneytrackerbackend.controller;

import hu.bmrk.bmoneytrackerbackend.entity.Category;
import hu.bmrk.bmoneytrackerbackend.entity.DTO.CategoryDTO;
import hu.bmrk.bmoneytrackerbackend.service.interfaces.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    @Autowired
    ModelMapper modelMapper;

    @GetMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<List<CategoryDTO>> getCategories(){
        List<CategoryDTO> categoryDTOS = new ArrayList<>();
        for(Category c : categoryService.findAll()){
            categoryDTOS.add(modelMapper.map(c, CategoryDTO.class));
        }
        return new ResponseEntity<>(categoryDTOS, HttpStatus.OK);
    }

    @PostMapping(
            path = "/new",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<CategoryDTO> createSaving(@RequestBody CategoryDTO category) {
        categoryService.saveCategory(modelMapper.map(category, Category.class));
        return new ResponseEntity<>(category, HttpStatus.OK);
    }


}
