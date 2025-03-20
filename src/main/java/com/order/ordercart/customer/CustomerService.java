package com.order.ordercart.customer;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.order.ordercart.customer.CustomerModel.Role;
import com.order.ordercart.jwtservice.JWTService;

import jakarta.transaction.Transactional;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private JWTService jwtService;

    // Register customer details
    public CustomerModel register(String name, String password, String email, String address, String phone_no,
            Role role) {

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
        customer.setPassword(bCryptPasswordEncoder.encode(password));
        customer.setEmail(email);
        customer.setAddress(address);
        customer.setPhoneNumber(phone_no);
        // customer.setRole(Role.USER); // default
        customer.setRole(role != null ? role : Role.USER);

        return customerRepository.save(customer);

    }

    // login customer details
    public String login(String email, String password) {
        if (email == null || email.isEmpty()) {
            throw new IllegalArgumentException("Please enter email");
        }

        if (password == null || password.isEmpty()) {
            throw new IllegalArgumentException("Please enter password");
        }

        CustomerModel customer = customerRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("User not found with this email"));

        if (!bCryptPasswordEncoder.matches(password, customer.getPassword())) {
            throw new IllegalArgumentException("Invalid password");
        }

        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(email, password));

        if (authentication.isAuthenticated()) {
            // return "Success";
            List<String> roles = List.of("ROLE_" + customer.getRole().name());
            return jwtService.generateToken(email, roles);
        }
        return "Failed";
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
        updateCustomer.setPassword(bCryptPasswordEncoder.encode(customer.getPassword()));
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
