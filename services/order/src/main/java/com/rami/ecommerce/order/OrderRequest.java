package com.rami.ecommerce.order;

import com.rami.ecommerce.product.PurchaseRequest;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.util.List;

public record OrderRequest(
        Integer id,
        String reference,
        @Positive(message="order amount should be positive")
        BigDecimal amount,
        @NotNull(message="payment method should be precised")
        PaymentMethod paymentMethod,
        @NotNull(message="customer should be present")
        @NotEmpty(message="order amount should be positive")
        @NotBlank(message="order amount should be positive")
        String customerId,
        @NotEmpty(message="You should at least purchase one product")
        List<PurchaseRequest> products
) {
}
