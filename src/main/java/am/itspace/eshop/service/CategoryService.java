package am.itspace.eshop.service;

import am.itspace.eshop.entity.Category;

import java.util.List;

public interface CategoryService {

    Category save(Category category);

    List<Category> findAll();

}
