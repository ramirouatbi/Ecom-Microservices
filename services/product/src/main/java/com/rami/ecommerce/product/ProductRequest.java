package com.rami.ecommerce.product;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record ProductRequest(

        Integer id,

        @NotNull(message = "Product name is required")
        String name,
        @NotNull(message = "Description name is required")
        String description,
        @Positive(message = "available quantity should be positive")
        double availableQuantity,
        @Positive(message = "price quantity should be positive")
        BigDecimal price,
        @NotNull(message = "product category is required")
        Integer categoryId
) {
}
