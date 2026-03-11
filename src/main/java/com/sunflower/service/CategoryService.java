package com.sunflower.service;

import com.sunflower.dto.CategoryDTO;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface CategoryService {

    List<CategoryDTO> getAllCategory();

    CategoryDTO saveCategory(CategoryDTO categoryDTO);

    void deleteCategory(Long categoryId);

    CategoryDTO updateCategory(Long categoryId, CategoryDTO categoryDTO);

    CategoryDTO getCategoryByName(String categoryName);

    List<CategoryDTO> searchCategoryByKeyword(String keyword);
}
