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
    public List<Category> findAll() {
        return categoryRepository.findAll();
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
    public Category findCategoryByTitle(String title) {
        return categoryRepository.findCategoryByTitle(title);
    }
}
