package hu.bmrk.bmoneytrackerbackend.service;


import hu.bmrk.bmoneytrackerbackend.entity.Category;
import hu.bmrk.bmoneytrackerbackend.entity.DTO.CategoryDTO;
import hu.bmrk.bmoneytrackerbackend.entity.UserEntity;
import hu.bmrk.bmoneytrackerbackend.repository.CategoryRepository;
import hu.bmrk.bmoneytrackerbackend.service.interfaces.CategoryService;
import hu.bmrk.bmoneytrackerbackend.util.HelperUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    HelperUtil helper;

    @Override
    public List<CategoryDTO> getCategoriesForUser(UserEntity user) {
        return helper.mapAll(categoryRepository.findAllByUserEntity_Id(user.getId()),CategoryDTO.class);
    }

    @Override
    public Category saveCategory(Category category) {
        return categoryRepository.save(category);
    }

}
