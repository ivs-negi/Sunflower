package com.sunflower.service;

import com.sunflower.requestDTO.ProductRequest;
import com.sunflower.responseDTO.ProductResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductService {

    // Public/User methods
    Page<ProductResponse> getAllProducts(Pageable pageable);

    ProductResponse getProductById(Long productId);

    ProductResponse getProductByName(String productName);

    Page<ProductResponse> searchProductByKeyword(String keyword, Pageable pageable);

    // Admin methods
    ProductResponse createProduct(ProductRequest productRequest);

    ProductResponse updateProduct(Long productId, ProductRequest productRequest);

    void deleteProduct(Long productId);
}