package com.sunflower.serviceImpl;

import com.sunflower.exception.CategoryNotFoundException;
import com.sunflower.exception.NameAlreadyExistsException;
import com.sunflower.model.Category;
import com.sunflower.repository.CategoryRepository;
import com.sunflower.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;


    // create category
    @Override
    public Category createCategory(Category category) {

        // checking if category already exist
        Category existingCategoryName = categoryRepository.findByCategoryName(category.getCategoryName());

        if(existingCategoryName!=null){
            // if exists throw exception
            throw new NameAlreadyExistsException(category.getCategoryName() + " name already exists.");
        }else {
            // if not exist save
            return categoryRepository.save(category);
        }
    }


    // get all categories
    @Override
    public List<Category> getAllCategory() {

        // checking id category is created or not
        List<Category> categoryList = categoryRepository.findAll();

        if (categoryList.isEmpty()){
            throw new CategoryNotFoundException("No categories found. Please create a category first.");
        } else {
           return categoryList;
        }
    }
}