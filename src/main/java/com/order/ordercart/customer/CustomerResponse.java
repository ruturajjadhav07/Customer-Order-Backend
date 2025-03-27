package com.order.ordercart.customer;

import lombok.Data;

@Data
public class CustomerResponse {
    private Long id;
    private String name;
    private String email;
    private String address;
    private String phoneNumber;
    private String role;

    public CustomerResponse(CustomerModel customer) {
        this.id = customer.getId();
        this.name = customer.getName();
        this.email = customer.getEmail();
        this.address = customer.getAddress();
        this.phoneNumber = customer.getPhoneNumber();
        this.role = customer.getRole().name();
    }
}
