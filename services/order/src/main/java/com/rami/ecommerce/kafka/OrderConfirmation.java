package com.rami.ecommerce.kafka;

import com.rami.ecommerce.customer.CustomerResponse;
import com.rami.ecommerce.order.PaymentMethod;
import com.rami.ecommerce.product.PurchaseResponse;

import java.math.BigDecimal;
import java.util.List;

public record OrderConfirmation(
        String orderReference,
        BigDecimal totalAmount,
        PaymentMethod paymentMethod,
        CustomerResponse customer,
        List<PurchaseResponse> products
) {
}
