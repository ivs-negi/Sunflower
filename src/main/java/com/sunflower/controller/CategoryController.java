package com.sunflower.controller;

import com.sunflower.dto.CategoryDTO;
import com.sunflower.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CategoryController {

    @Autowired
    private CategoryService categoryService;


    // get all categories
    @GetMapping("/public/categories")
    public ResponseEntity<List<CategoryDTO>> getAllcategories(){
        List<CategoryDTO> categoryList = categoryService.getAllCategory();
        return ResponseEntity.status(HttpStatus.FOUND).body(categoryList);
    }

    // get category by name
    @GetMapping("/admin/category/name/{categoryName}")
    public ResponseEntity<CategoryDTO> getCategoryByName(@PathVariable String categoryName) {

        CategoryDTO categoryDTO = categoryService.getCategoryByName(categoryName);

        return ResponseEntity.ok(categoryDTO);
    }

    // create category
    @PostMapping("/admin/category")
    public ResponseEntity<CategoryDTO> createCategory(@Valid @RequestBody CategoryDTO categoryDTO) {
        CategoryDTO savedCategory = categoryService.saveCategory(categoryDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedCategory);
    }

    // delete category
    @DeleteMapping("/admin/category/{categoryId}")
    public ResponseEntity<String> deleteCategory(@PathVariable Long categoryId) {

        categoryService.deleteCategory(categoryId);

        return ResponseEntity.ok("Category deleted successfully.");
    }

    // update category
    @PutMapping("/admin/category/{categoryId}")
    public ResponseEntity<CategoryDTO> updateCategory(@PathVariable Long categoryId,
            @Valid @RequestBody CategoryDTO categoryDTO) {

        CategoryDTO updatedCategory = categoryService.updateCategory(categoryId, categoryDTO);

        return ResponseEntity.ok(updatedCategory);
    }





























}
