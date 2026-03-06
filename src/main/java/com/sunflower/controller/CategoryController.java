package com.sunflower.controller;

import com.sunflower.model.Category;
import com.sunflower.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping("/admin/category")
    public ResponseEntity<Category> createCategory(@Valid @RequestBody Category category){
       Category savedcategory = categoryService.createCategory(category);
        return ResponseEntity.ok(savedcategory);
    }

    @GetMapping("/public/categories")
    public ResponseEntity<List<Category>> getAllcategories(){
        List<Category> categoryList = categoryService.getAllCategory();
        return ResponseEntity.ok(categoryList);
    }
}
