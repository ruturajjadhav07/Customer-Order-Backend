package com.order.ordercart.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    // Register
    public CustomerModel register(String name, String password, String email, String address, String phone_no) {

        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Please enter name");
        }

        if (password == null || password.isEmpty()) {
            throw new IllegalArgumentException("Please enter password");
        }

        if (email == null || email.isEmpty()) {
            throw new IllegalArgumentException("Please enter email");
        }

        if (customerRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("Email already registered");
        }

        if (address == null || address.isEmpty()) {
            throw new IllegalArgumentException("Please enter address");
        }

        if (phone_no == null || phone_no.isEmpty()) {
            throw new IllegalArgumentException("Please enter phone number");
        }

        CustomerModel customer = new CustomerModel();
        customer.setName(name);
        customer.setPassword(password);
        customer.setEmail(email);
        customer.setAddress(address);
        customer.setPhoneNumber(phone_no);

        return customerRepository.save(customer);

    }

    // login
    public CustomerModel login(String email, String password) {
        if (email == null || email.isEmpty()) {
            throw new IllegalArgumentException("Please enter email");
        }

        if (password == null || password.isEmpty()) {
            throw new IllegalArgumentException("Please enter password");
        }

        CustomerModel customer = customerRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("User not found with this email"));

        if (!customer.getPassword().equals(password)) {
            throw new IllegalArgumentException("Invalid password");
        }

        return customer;
    }
}
