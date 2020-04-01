package hu.bmrk.bmoneytrackerbackend.service.interfaces;

import hu.bmrk.bmoneytrackerbackend.entity.Category;

import java.util.List;

public interface CategoryService {

    List<Category> findAllByUserEntity_Id(Long id);

    void deleteCategory(Long id);

    Category findById(Long id);

    Category saveCategory(Category category);

    Category editCategory(Category category);

    Category findCategoryByTitleAndUserEntity_Id(String title, Long id);
}
