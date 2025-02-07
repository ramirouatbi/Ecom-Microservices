package com.rami.ecommerce.order;

import com.rami.ecommerce.exception.BusinessException;
import com.rami.ecommerce.customer.CustomerClient;
import com.rami.ecommerce.kafka.OrderConfirmation;
import com.rami.ecommerce.kafka.OrderProducer;
import com.rami.ecommerce.orderLine.OrderLineRequest;
import com.rami.ecommerce.orderLine.OrderLineService;
import com.rami.ecommerce.product.ProductClient;
import com.rami.ecommerce.product.PurchaseRequest;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository repository;
    private final CustomerClient customerClient;
    private final ProductClient productClient;
    private final OrderMapper mapper;
    private final OrderLineService orderLineService;
    private final OrderProducer orderProducer;

    public Integer createdOrder(OrderRequest request) {
        var customer = customerClient.findCustomerById(request.customerId())
                .orElseThrow(() -> new BusinessException("Cannot create order::NO Customer exists with the provited ID"));

        var purchasedProducts = this.productClient.purchaseProducts(request.products());
        
        var order = this.repository.save(mapper.toOrder(request));

        for(PurchaseRequest purchaseRequest: request.products()){
            orderLineService.saveOrderLine(
                    new OrderLineRequest(
                            null,
                            order.getId(),
                            purchaseRequest.productId(),
                            purchaseRequest.quantity()
                    )
            );
        }

        // todo  start payment process
        orderProducer.sendOrderConfirmation(
                new OrderConfirmation(
                        request.reference(),
                        request.amount(),
                        request.paymentMethod(),
                        customer,
                        purchasedProducts
                )
        );

        return order.getId();
    }

    public List<OrderResponse> findAll() {
        return repository.findAll()
                .stream()
                .map(mapper::fromOrder)
                .collect(Collectors.toList());
    }

    public OrderResponse findById(Integer orderId) {
        return repository.findById(orderId)
                .map(mapper::fromOrder)
                .orElseThrow(() -> new EntityNotFoundException(String.format("No order found with the provided ID : %d",orderId)));
    }
}
