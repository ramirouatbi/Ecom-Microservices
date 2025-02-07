package com.rami.ecommerce.payment;

import com.rami.ecommerce.customer.CustomerResponse;
import com.rami.ecommerce.order.PaymentMethod;

import java.math.BigDecimal;

public record PaymentRequest(
        BigDecimal amount,
        PaymentMethod paymentMethod,
        Integer orderId,
        String orderReference,
        CustomerResponse customer
) {
}
