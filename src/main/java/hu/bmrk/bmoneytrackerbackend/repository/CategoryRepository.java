package hu.bmrk.bmoneytrackerbackend.repository;

import hu.bmrk.bmoneytrackerbackend.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, String> {

    List<Category> findAll();

    Category findCategoryByTitle(String title);

}
