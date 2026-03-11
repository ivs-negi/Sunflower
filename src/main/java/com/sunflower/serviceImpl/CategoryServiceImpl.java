package com.sunflower.serviceImpl;

import com.sunflower.dto.CategoryRequest;
import com.sunflower.dto.CategoryResponse;
import com.sunflower.exception.CategoryNotFoundException;
import com.sunflower.exception.NameAlreadyExistsException;
import com.sunflower.model.Category;
import com.sunflower.repository.CategoryRepository;
import com.sunflower.service.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;

    // get all categories
    @Override
    public List<CategoryResponse> getAllCategory() {
        List<Category> categories = categoryRepository.findAll();

        return categories.stream()
                .map(category -> modelMapper.map(category, CategoryResponse.class))
                .toList();
    }

    // get category by name
    @Override
    public CategoryResponse getCategoryByName(String categoryName) {
        Category category = categoryRepository
                .findByCategoryName(categoryName)
                .orElseThrow(() ->
                        new CategoryNotFoundException("Category not found with name: " + categoryName)
                );
        return modelMapper.map(category, CategoryResponse.class);
    }

    // get category by id
    @Override
    public CategoryResponse getCategoryById(Long categoryId) {
        Category category = categoryRepository
                .findById(categoryId)
                .orElseThrow(() ->
                        new CategoryNotFoundException("Category not found with id: " + categoryId)
                );
        return modelMapper.map(category, CategoryResponse.class);
    }

    // get category by keyword
    @Override
    public List<CategoryResponse> searchCategoryByKeyword(String keyword) {
        List<Category> categories =
                categoryRepository.findByCategoryNameContainingIgnoreCase(keyword);

        if (categories.isEmpty()) {
            throw new CategoryNotFoundException("No category found with keyword: " + keyword);
        }

        return categories.stream()
                .map(category -> modelMapper.map(category, CategoryResponse.class))
                .toList();
    }

    // saving category - now accepts CategoryRequest, returns CategoryResponse
    @Override
    public CategoryResponse saveCategory(CategoryRequest categoryRequest) {
        // checking if category already exists
        if (categoryRepository.existsByCategoryName(categoryRequest.getCategoryName())) {
            throw new NameAlreadyExistsException(categoryRequest.getCategoryName() + " name already exists.");
        }

        // Convert Request to Entity
        Category category = modelMapper.map(categoryRequest, Category.class);
        Category savedCategory = categoryRepository.save(category);

        // Convert Entity to Response
        return modelMapper.map(savedCategory, CategoryResponse.class);
    }

    // delete category
    @Override
    public void deleteCategory(Long categoryId) {
        if (!categoryRepository.existsById(categoryId)) {
            throw new CategoryNotFoundException("Category not found with id: " + categoryId);
        }
        categoryRepository.deleteById(categoryId);
    }

    // update category - now accepts CategoryRequest, returns CategoryResponse
    @Override
    public CategoryResponse updateCategory(Long categoryId, CategoryRequest categoryRequest) {
        Category existingCategory = categoryRepository.findById(categoryId)
                .orElseThrow(() ->
                        new CategoryNotFoundException("Category not found with id: " + categoryId));

        // Check if new name already exists (and it's not the current category)
        if (!existingCategory.getCategoryName().equals(categoryRequest.getCategoryName()) &&
                categoryRepository.existsByCategoryName(categoryRequest.getCategoryName())) {
            throw new NameAlreadyExistsException(
                    categoryRequest.getCategoryName() + " name already exists with another category."
            );
        }

        // Update fields from Request
        existingCategory.setCategoryName(categoryRequest.getCategoryName());

        Category savedCategory = categoryRepository.save(existingCategory);

        // Return Response DTO
        return modelMapper.map(savedCategory, CategoryResponse.class);
    }
}