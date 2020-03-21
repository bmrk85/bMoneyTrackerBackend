package hu.bmrk.bmoneytrackerbackend.service;


import hu.bmrk.bmoneytrackerbackend.entity.Category;
import hu.bmrk.bmoneytrackerbackend.repository.CategoryRepository;
import hu.bmrk.bmoneytrackerbackend.service.interfaces.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;


    @Override
    public List<Category> findAllByUserEntity_Id(Long id) {
        return categoryRepository.findAllByUserEntity_Id(id);
    }

    @Override
    public void deleteCategory(String title) {
        categoryRepository.deleteById(title);
    }

    @Override
    public Category saveCategory(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public Category findCategoryByTitleAndUserEntity_Id(String title, Long id) {
        return categoryRepository.findCategoryByTitleAndUserEntity_Id(title, id);
    }
}
