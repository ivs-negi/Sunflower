package com.sunflower.service;

import com.sunflower.requestDTO.CategoryRequest;
import com.sunflower.responseDTO.CategoryResponse;
import java.util.List;

public interface CategoryService {

    // User endpoints
    List<CategoryResponse> getAllCategory();
    CategoryResponse getCategoryByName(String categoryName);
    CategoryResponse getCategoryById(Long categoryId);
    List<CategoryResponse> searchCategoryByKeyword(String keyword);

    // Admin endpoints
    CategoryResponse saveCategory(CategoryRequest categoryRequest);
    CategoryResponse updateCategory(Long categoryId, CategoryRequest categoryRequest);
    void deleteCategory(Long categoryId);
}