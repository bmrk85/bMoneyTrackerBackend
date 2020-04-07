package hu.bmrk.bmoneytrackerbackend.service.interfaces;

import hu.bmrk.bmoneytrackerbackend.entity.Category;
import hu.bmrk.bmoneytrackerbackend.entity.DTO.CategoryDTO;
import hu.bmrk.bmoneytrackerbackend.entity.UserEntity;

import java.util.List;

public interface CategoryService {

    List<CategoryDTO> getCategoriesForUser(UserEntity user);

    Category saveCategory(Category category);

    void deleteById(Long id);


}
