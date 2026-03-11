package com.sunflower.serviceImpl;

import com.sunflower.requestDTO.ProductRequest;
import com.sunflower.responseDTO.ProductResponse;
import com.sunflower.exception.ResourceNotFoundException;
import com.sunflower.exception.NameAlreadyExistsException;
import com.sunflower.model.Category;
import com.sunflower.model.Product;
import com.sunflower.repository.CategoryRepository;
import com.sunflower.repository.ProductRepository;
import com.sunflower.service.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;

    // get all products with pagination
    @Override
    public Page<ProductResponse> getAllProducts(Pageable pageable) {
        Page<Product> products = productRepository.findAll(pageable);
        return products.map(product -> modelMapper.map(product, ProductResponse.class));
    }

    // get product by id
    @Override
    public ProductResponse getProductById(Long productId) {
        Product product = productRepository
                .findById(productId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Product not found with id: " + productId)
                );
        return modelMapper.map(product, ProductResponse.class);
    }

    // get product by name
    @Override
    public ProductResponse getProductByName(String productName) {
        Product product = productRepository
                .findByProductName(productName)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Product not found with name: " + productName)
                );
        return modelMapper.map(product, ProductResponse.class);
    }

    // search product by keyword with pagination
    @Override
    public Page<ProductResponse> searchProductByKeyword(String keyword, Pageable pageable) {
        Page<Product> products = productRepository.findByProductNameContainingIgnoreCase(keyword, pageable);

        if (products.isEmpty()) {
            throw new ResourceNotFoundException("No products found with keyword: " + keyword);
        }

        return products.map(product -> modelMapper.map(product, ProductResponse.class));
    }

    // create product
    @Override
    public ProductResponse createProduct(ProductRequest productRequest) {
        // checking if product with same name already exists
        if (productRepository.existsByProductName(productRequest.getProductName())) {
            throw new NameAlreadyExistsException(productRequest.getProductName() + " name already exists.");
        }

        // checking if category exists
        Category category = categoryRepository
                .findById(productRequest.getCategoryId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Category not found with id: " + productRequest.getCategoryId())
                );

        // Convert Request to Entity
        Product product = modelMapper.map(productRequest, Product.class);
        product.setCategory(category); // Set the category manually

        Product savedProduct = productRepository.save(product);

        // Convert Entity to Response
        return modelMapper.map(savedProduct, ProductResponse.class);
    }

    // delete product
    @Override
    public void deleteProduct(Long productId) {
        if (!productRepository.existsById(productId)) {
            throw new ResourceNotFoundException("Product not found with id: " + productId);
        }
        productRepository.deleteById(productId);
    }

    // update product
    @Override
    public ProductResponse updateProduct(Long productId, ProductRequest productRequest) {
        Product existingProduct = productRepository.findById(productId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Product not found with id: " + productId));

        // Check if new name already exists (and it's not the current product)
        if (!existingProduct.getProductName().equals(productRequest.getProductName()) &&
                productRepository.existsByProductName(productRequest.getProductName())) {
            throw new NameAlreadyExistsException(
                    productRequest.getProductName() + " name already exists with another product."
            );
        }

        // Check if category exists if categoryId is provided
        if (productRequest.getCategoryId() != null) {
            Category category = categoryRepository
                    .findById(productRequest.getCategoryId())
                    .orElseThrow(() ->
                            new ResourceNotFoundException("Category not found with id: " + productRequest.getCategoryId())
                    );
            existingProduct.setCategory(category);
        }

        // Update fields from Request
        existingProduct.setProductName(productRequest.getProductName());
        existingProduct.setDescription(productRequest.getDescription());
        existingProduct.setPrice(productRequest.getPrice());
        existingProduct.setQuantity(productRequest.getQuantity());
        existingProduct.setImageUrl(productRequest.getImageUrl());

        Product savedProduct = productRepository.save(existingProduct);

        // Return Response DTO
        return modelMapper.map(savedProduct, ProductResponse.class);
    }
}