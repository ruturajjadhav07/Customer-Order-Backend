package com.order.ordercart.customer;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.order.ordercart.jwtservice.JWTService;

@RestController
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @SuppressWarnings("unused")
    @Autowired
    private JWTService jwtService;

    // Register customer
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody CustomerModel customer) {
        customerService.register(customer.getName(), customer.getPassword(), customer.getEmail(), customer.getAddress(),
                customer.getPhoneNumber(), customer.getRole());
        return ResponseEntity.ok("Registered Successfully");
    }

    // Login customer
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody CustomerModel customer) {
        String token = customerService.login(customer.getEmail(), customer.getPassword());

        if (!token.equals("Failed")) {
            return ResponseEntity.ok(token);
        }

        return ResponseEntity.status(401).body("Invalid credentials");
    }

    // ✅ Get customer details (only for the logged-in user)
    @GetMapping("/customer/{id}")
    public ResponseEntity<?> getCustomer(@PathVariable long id, Principal principal) {
        String loggedInEmail = principal.getName(); // Extract email from token

        CustomerModel loggedInUser = customerService.getCustomerByEmail(loggedInEmail);

        // Restrict access: Users can only view their own details
        if (loggedInUser.getId() != id) {
            return ResponseEntity.status(403).body("Access denied! You can only view your own details.");
        }

        return ResponseEntity.ok(loggedInUser);
    }

    // ✅ Update customer details (only for the logged-in user)
    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateCustomer(@PathVariable long id, @RequestBody CustomerModel customer,
            Principal principal) {
        String loggedInEmail = principal.getName();
        CustomerModel loggedInUser = customerService.getCustomerByEmail(loggedInEmail);

        if (loggedInUser.getId() != id) {
            return ResponseEntity.status(403).body("Access denied! You can only update your own profile.");
        }

        customerService.updateCustomer(id, customer);
        return ResponseEntity.ok("Updated Successfully");
    }

    // ✅ Delete customer (only for the logged-in user)
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteCustomer(@PathVariable long id, Principal principal) {
        String loggedInEmail = principal.getName();
        CustomerModel loggedInUser = customerService.getCustomerByEmail(loggedInEmail);

        if (loggedInUser.getId() != id) {
            return ResponseEntity.status(403).body("Access denied! You can only delete your own profile.");
        }

        customerService.deleteCustomer(id);
        return ResponseEntity.ok("Customer deleted successfully");
    }

    // ✅ Get all customers (Only for Admins)
    @GetMapping("/admin/customers")
    public ResponseEntity<?> getAllCustomers(Principal principal) {
        String loggedInEmail = principal.getName();
        CustomerModel loggedInUser = customerService.getCustomerByEmail(loggedInEmail);

        if (!"ADMIN".equals(loggedInUser.getRole())) {
            return ResponseEntity.status(403).body("Access denied! Only admins can view all customers.");
        }

        return ResponseEntity.ok(customerService.getAllCustomers());
    }
}
