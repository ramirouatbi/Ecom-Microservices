package com.rami.ecommerce.product;

import jakarta.validation.constraints.NotNull;

public record ProductPurchaseRequest(

        @NotNull(message = "Product id mandatory")
        Integer productId,
        @NotNull(message = "Quantity id mandatory")
        double quantity
) {
}
