package com.sunflower.repository;

import com.sunflower.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    Optional<Product> findByProductName(String productName);

    Page<Product> findByProductNameContainingIgnoreCase(String keyword, Pageable pageable);

    boolean existsByProductName(String productName);

    Page<Product> findByCategoryId(Long categoryId, Pageable pageable);

    Page<Product> findByPriceBetween(Double minPrice, Double maxPrice, Pageable pageable);

    Page<Product> findByQuantityLessThan(Integer threshold, Pageable pageable);
}