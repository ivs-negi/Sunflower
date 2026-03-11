package com.sunflower.controller;

import com.sunflower.requestDTO.CategoryRequest;
import com.sunflower.responseDTO.CategoryResponse;
import com.sunflower.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    //    **************************** PUBLIC/USER endpoints ***********************************

    // get all categories
    @GetMapping("/categories")
    public ResponseEntity<List<CategoryResponse>> getAllCategories() {
        List<CategoryResponse> categoryList = categoryService.getAllCategory();
        return ResponseEntity.ok(categoryList);
    }

    // get category by name
    @GetMapping("/category/name/{categoryName}")
    public ResponseEntity<CategoryResponse> getCategoryByName(@PathVariable String categoryName) {
        CategoryResponse categoryResponse = categoryService.getCategoryByName(categoryName);
        return ResponseEntity.ok(categoryResponse);
    }

    // get category by id
    @GetMapping("/category/{categoryId}")
    public ResponseEntity<CategoryResponse> getCategoryById(@PathVariable Long categoryId) {
        CategoryResponse categoryResponse = categoryService.getCategoryById(categoryId);
        return ResponseEntity.ok(categoryResponse);
    }

    // search category by keyword
    @GetMapping("/categories/search")
    public ResponseEntity<List<CategoryResponse>> searchCategoryByKeyword(@RequestParam String keyword) {
        List<CategoryResponse> categories = categoryService.searchCategoryByKeyword(keyword);
        return ResponseEntity.ok(categories);
    }

    //    ********************* ADMIN endpoints *********************************

    // create category
    @PostMapping("/admin/category")
    public ResponseEntity<CategoryResponse> createCategory(@Valid @RequestBody CategoryRequest categoryRequest) {
        CategoryResponse savedCategory = categoryService.saveCategory(categoryRequest);
        return new ResponseEntity<>(savedCategory, HttpStatus.CREATED);
    }

    // delete category
    @DeleteMapping("/admin/category/{categoryId}")
    public ResponseEntity<String> deleteCategory(@PathVariable Long categoryId) {
        categoryService.deleteCategory(categoryId);
        return ResponseEntity.ok("Category deleted successfully.");
    }

    // update category - using CategoryRequest (not CategoryDTO)
    @PutMapping("/admin/category/{categoryId}")
    public ResponseEntity<CategoryResponse> updateCategory(
            @PathVariable Long categoryId,
            @Valid @RequestBody CategoryRequest categoryRequest) {
        CategoryResponse updatedCategory = categoryService.updateCategory(categoryId, categoryRequest);
        return ResponseEntity.ok(updatedCategory);
    }
}