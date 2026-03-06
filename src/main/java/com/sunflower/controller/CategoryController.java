package com.sunflower.controller;

import com.sunflower.dto.CategoryDTO;
import com.sunflower.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CategoryController {

    @Autowired
    private CategoryService categoryService;


//    **************************** user control ***********************************


    // get all categories

    @GetMapping("/categories")
    public ResponseEntity<List<CategoryDTO>> getAllCategories(){

        List<CategoryDTO> categoryList = categoryService.getAllCategory();

        return ResponseEntity.ok(categoryList);
    }

    // get category by name

    @GetMapping("category/name/{categoryName}")
    public ResponseEntity<CategoryDTO> getCategoryByName(@PathVariable String categoryName) {

        CategoryDTO categoryDTO = categoryService.getCategoryByName(categoryName);

        return ResponseEntity.ok(categoryDTO);
    }

    //  get category by keyword

    @GetMapping("/categories/search")
    public ResponseEntity<List<CategoryDTO>> searchCategoryByKeyword(@RequestParam String keyword) {

        List<CategoryDTO> categories = categoryService.searchCategoryByKeyword(keyword);

        return ResponseEntity.ok(categories);
    }


//    ********************* ADMIN Control *********************************


    // create category

    @PostMapping("/admin/category")
    public ResponseEntity<CategoryDTO> createCategory(@Valid @RequestBody CategoryDTO categoryDTO) {

        CategoryDTO savedCategory = categoryService.saveCategory(categoryDTO);

        return ResponseEntity.ok(savedCategory);
    }

    // delete category

    @DeleteMapping("/admin/category/{categoryId}")
    public ResponseEntity<String> deleteCategory(@PathVariable Long categoryId) {

        categoryService.deleteCategory(categoryId);

        return ResponseEntity.ok("Category deleted successfully.");
    }

    // update category

    @PutMapping("/admin/category/{categoryId}")
    public ResponseEntity<CategoryDTO> updateCategory(@PathVariable Long categoryId, @Valid @RequestBody CategoryDTO categoryDTO) {

        CategoryDTO updatedCategory = categoryService.updateCategory(categoryId, categoryDTO);

        return ResponseEntity.ok(updatedCategory);
    }

}