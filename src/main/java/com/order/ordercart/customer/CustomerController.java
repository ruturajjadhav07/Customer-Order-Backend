package com.order.ordercart.customer;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.order.ordercart.jwtservice.JWTService;

@RestController
// @RequestMapping("/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private JWTService jwtService;

    // register customer details
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody CustomerModel customer) {
        customerService.register(customer.getName(), customer.getPassword(), customer.getEmail(), customer.getAddress(),
                customer.getPhoneNumber());
        return ResponseEntity.ok("Registered Successfully");
    }

    // login customer details
    @PostMapping("/login")
    // public ResponseEntity<String> login(@RequestBody CustomerModel customer) {
    // customerService.login(customer.getEmail(), customer.getPassword());
    // return ResponseEntity.ok("Login Successful");
    // }

    public ResponseEntity<String> login(@RequestBody CustomerModel customer) {
        String Authenticated = customerService.login(customer.getEmail(), customer.getPassword());

        if (Authenticated != null) {
            String token = jwtService.generateToken(customer.getEmail());
            return ResponseEntity.ok(token);
        }

        return ResponseEntity.status(401).body("Invalid credentials");
    }

    // update customer details
    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateCustomer(@PathVariable long id, @RequestBody CustomerModel customer) {
        customerService.updateCustomer(id, customer);
        return ResponseEntity.ok("Updated Successfully");
    }

    // delete customer
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteCustomer(@PathVariable long id) {
        customerService.deleteCustomer(id);
        return ResponseEntity.ok("Customer deleted successfully");
    }

    // get all customers
    @GetMapping()
    public List<CustomerModel> getAllCustomers() {
        return customerService.getAllCustomers();
    }

}
