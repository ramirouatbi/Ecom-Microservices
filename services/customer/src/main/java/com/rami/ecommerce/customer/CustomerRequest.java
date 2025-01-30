package com.rami.ecommerce.customer;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.annotation.Id;

public record CustomerRequest(

     String id,

     @NotNull(message= "Customer firstname is required")
     String firstName,
     @NotNull(message= "Customer lastname is required")
     String lastName,
     @NotNull(message= "Customer email is required")
     @Email(message="customer email is not valid email address")
     String email,
     Address address
){

}
