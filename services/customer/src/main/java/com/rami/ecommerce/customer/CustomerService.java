package com.rami.ecommerce.customer;

import com.rami.ecommerce.exception.CustomerNotFoundException;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class CustomerService {


    private final CustomerRepository repository;

    private final CustomerMapper mapper;


    public String createCustomer(CustomerRequest request) {
        var customer = this.repository.save(mapper.toCustomer(request));
        return customer.getId();
    }


    public void updateCustomer(CustomerRequest request) {
        var customer = this.repository.findById(request.id())
                .orElseThrow(() -> new CustomerNotFoundException(
                        String.format("Cannot update Customer:: No Customer Found with the provided Id :: %s",request.id())
                ));
        mergerCustomer(customer, request);
        this.repository.save(customer);
    }

    private void mergerCustomer(Customer customer, CustomerRequest request) {
        if(StringUtils.isNotBlank(request.firstname())){
            customer.setFirstname(request.firstname());
        }

        if(StringUtils.isNotBlank(request.email())){
            customer.setEmail(request.email());
        }
        if(request.address()!= null){
            customer.setAddress(request.address());
        }


    }


    public List<CustomerResponse> findAllCustomer() {
        return this.repository.findAll()
                .stream()
                .map(mapper::fromCustomer)
                .collect(Collectors.toList());
    }

    public Boolean existsById(String customerId) {
        return this.repository.findById(customerId)
                .isPresent();
    }

    public CustomerResponse findById(String customerId) {
        return this.repository.findById(customerId)
                .map(mapper::fromCustomer)
                .orElseThrow(() -> new CustomerNotFoundException(String.format("No customer found with the provided id ::%s",customerId)));
    }

    public void deleteCustomer(String customerId) {
        this.repository.deleteById(customerId);
    }
}
