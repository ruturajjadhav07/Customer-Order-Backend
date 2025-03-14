package com.order.ordercart.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    // register
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody CustomerModel customer) {
        customerService.register(customer.getName(), customer.getPassword(), customer.getEmail(), customer.getAddress(),
                customer.getPhoneNumber());
        return ResponseEntity.ok("Registered Successfully");
    }

    // login
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody CustomerModel customer) {
        customerService.login(customer.getEmail(), customer.getPassword());
        return ResponseEntity.ok("Login Successful");
    }

}
