package com.sunflower.serviceImpl;

import com.sunflower.dto.CategoryDTO;
import com.sunflower.exception.CategoryNotFoundException;
import com.sunflower.exception.NameAlreadyExistsException;
import com.sunflower.model.Category;
import com.sunflower.repository.CategoryRepository;
import com.sunflower.service.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;


    // get all categories
    @Override
    public List<CategoryDTO> getAllCategory() {
        List<Category> categories = categoryRepository.findAll();

        return categories
                .stream()
                .map(category -> modelMapper.map(category, CategoryDTO.class))
                .toList();
    }

    // saving category
    @Override
    public CategoryDTO saveCategory(CategoryDTO categoryDTO) {

        // checking if category already exist
        if (categoryRepository.existsByCategoryName(categoryDTO.getCategoryName())){
            throw new NameAlreadyExistsException(categoryDTO.getCategoryName()+" name already exist.");
        }

        Category category = modelMapper.map(categoryDTO, Category.class);
        Category savedCategory = categoryRepository.save(category);
        return modelMapper.map(savedCategory, CategoryDTO.class);
    }

   // delete category
   @Override
   public void deleteCategory(Long categoryId) {

       Category category = categoryRepository.findById(categoryId)
               .orElseThrow(() ->
                       new CategoryNotFoundException("Category not found with id: " + categoryId));

       categoryRepository.delete(category);
   }

   // update category
    @Override
    public CategoryDTO updateCategory(Long categoryId, CategoryDTO categoryDTO) {

        Category existingCategory = categoryRepository.findById(categoryId)
                .orElseThrow(() ->
                        new CategoryNotFoundException(
                                "Category not found with id: " + categoryId
                        ));

        // update fields
        existingCategory.setCategoryName(categoryDTO.getCategoryName());

        Category savedCategory = categoryRepository.save(existingCategory);

        return modelMapper.map(savedCategory, CategoryDTO.class);
    }
}

