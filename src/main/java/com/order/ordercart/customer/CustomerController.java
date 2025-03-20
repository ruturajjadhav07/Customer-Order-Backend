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

    @SuppressWarnings("unused")
    @Autowired
    private JWTService jwtService;

    // register customer details
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody CustomerModel customer) {
        customerService.register(customer.getName(), customer.getPassword(), customer.getEmail(), customer.getAddress(),
                customer.getPhoneNumber(), customer.getRole());
        return ResponseEntity.ok("Registered Successfully");
    }

    // login customer details
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody CustomerModel customer) {
        String token = customerService.login(customer.getEmail(), customer.getPassword());

        if (!token.equals("Failed")) {
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
    @GetMapping("/admin/customers")
    public List<CustomerModel> getAllCustomers() {
        return customerService.getAllCustomers();
    }

}
