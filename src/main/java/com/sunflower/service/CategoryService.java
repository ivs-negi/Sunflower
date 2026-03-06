package com.sunflower.service;

import com.sunflower.model.Category;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface CategoryService {

    Category createCategory(Category category);

    List<Category> getAllCategory();
}
