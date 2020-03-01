package hu.bmrk.bmoneytrackerbackend.service.interfaces;

import hu.bmrk.bmoneytrackerbackend.entity.Category;

import java.util.List;

public interface CategoryService {

    List<Category> findAll();

    void deleteCategory(String title);

    Category saveCategory(Category category);
}
