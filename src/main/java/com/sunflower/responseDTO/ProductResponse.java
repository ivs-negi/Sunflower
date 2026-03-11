package com.sunflower.responseDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponse {

    private Long id;
    private String productName;
    private String description;
    private BigDecimal price;
    private Integer quantity;
    private String imageUrl;
    private Long categoryId;
    private String categoryName;
}