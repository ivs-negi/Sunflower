package com.sunflower.controller;

import com.sunflower.requestDTO.ProductRequest;
import com.sunflower.responseDTO.ProductResponse;
import com.sunflower.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ProductController {

    @Autowired
    private ProductService productService;

    // **************************** PUBLIC/USER endpoints ***********************************

    // get all products with pagination
    @GetMapping("/products")
    public ResponseEntity<Page<ProductResponse>> getAllProducts(
            @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
        Page<ProductResponse> productList = productService.getAllProducts(pageable);
        return ResponseEntity.ok(productList);
    }

    // get product by id
    @GetMapping("/product/{productId}")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable Long productId) {
        ProductResponse productResponse = productService.getProductById(productId);
        return ResponseEntity.ok(productResponse);
    }

    // get product by name
    @GetMapping("/product/name/{productName}")
    public ResponseEntity<ProductResponse> getProductByName(@PathVariable String productName) {
        ProductResponse productResponse = productService.getProductByName(productName);
        return ResponseEntity.ok(productResponse);
    }

    // search product by keyword with pagination
    @GetMapping("/products/search")
    public ResponseEntity<Page<ProductResponse>> searchProductByKeyword(
            @RequestParam String keyword,
            @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
        Page<ProductResponse> products = productService.searchProductByKeyword(keyword, pageable);
        return ResponseEntity.ok(products);
    }

    // **************************** ADMIN endpoints ***********************************

    // create product
    @PostMapping("/admin/product")
    public ResponseEntity<ProductResponse> createProduct(@Valid @RequestBody ProductRequest productRequest) {
        ProductResponse savedProduct = productService.createProduct(productRequest);
        return new ResponseEntity<>(savedProduct, HttpStatus.CREATED);
    }

    // delete product
    @DeleteMapping("/admin/product/{productId}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long productId) {
        productService.deleteProduct(productId);
        return ResponseEntity.ok("Product deleted successfully.");
    }

    // update product
    @PutMapping("/admin/product/{productId}")
    public ResponseEntity<ProductResponse> updateProduct(
            @PathVariable Long productId,
            @Valid @RequestBody ProductRequest productRequest) {
        ProductResponse updatedProduct = productService.updateProduct(productId, productRequest);
        return ResponseEntity.ok(updatedProduct);
    }
}