package com.order.ordercart.customer;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    // Register customer details
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

    // login customer details
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

    // update customer details

    @Transactional

    public CustomerModel updateCustomer(long id, CustomerModel customer) {
        CustomerModel updateCustomer = customerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Customer not found"));

        if (customer.getName() == null || customer.getName().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be empty");
        }

        if (customer.getPassword() == null || customer.getPassword().isEmpty()) {
            throw new IllegalArgumentException("Password cannot be empty");
        }

        if (customer.getEmail() == null || customer.getEmail().isEmpty()) {
            throw new IllegalArgumentException("Email cannot be empty");
        }

        if (!customer.getEmail().equals(updateCustomer.getEmail())
                && customerRepository.existsByEmail(customer.getEmail())) {
            throw new IllegalArgumentException("Email already in use");
        }

        if (customer.getAddress() == null || customer.getAddress().isEmpty()) {
            throw new IllegalArgumentException("Address cannot be empty");
        }

        if (customer.getPhoneNumber() == null || customer.getPhoneNumber().isEmpty()) {
            throw new IllegalArgumentException("Phone number cannot be empty");
        }

        updateCustomer.setName(customer.getName());
        updateCustomer.setPassword(customer.getPassword());
        updateCustomer.setEmail(customer.getEmail());
        updateCustomer.setAddress(customer.getAddress());
        updateCustomer.setPhoneNumber(customer.getPhoneNumber());

        return customerRepository.save(updateCustomer);
    }

    // delete customer
    public void deleteCustomer(long id) {
        CustomerModel customer = customerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("user not found"));
        customerRepository.deleteById(customer.getId());
    }

    // get all customers
    public List<CustomerModel> getAllCustomers() {
        return customerRepository.findAll();
    }

}
