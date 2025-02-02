package com.rami.ecommerce.order;

import com.rami.ecommerce.exception.BusinessException;
import com.rami.ecommerce.customer.CustomerClient;
import com.rami.ecommerce.orderLine.OrderLineRequest;
import com.rami.ecommerce.orderLine.OrderLineService;
import com.rami.ecommerce.product.ProductClient;
import com.rami.ecommerce.product.PurchaseRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository repository;
    private final CustomerClient customerClient;
    private final ProductClient productClient;
    private final OrderMapper mapper;
    private final OrderLineService orderLineService;

    public Integer createdOrder(OrderRequest request) {
        var customer = customerClient.findCustomerById(request.customerId())
                .orElseThrow(() -> new BusinessException("Cannot create order::NO Customer exists with the provited ID"));

        this.productClient.purchaseProducts(request.products());
        
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
        // send the order confirmation  -> notification-ms (kafka)
        return null;
    }
}
