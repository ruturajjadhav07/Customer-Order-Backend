package com.order.ordercart.oders;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.order.ordercart.orderitem.OrderItemReq;

@RestController
@RequestMapping("/customer")
public class OrderController {

    @Autowired
    private OrderService orderService;

    // create a order
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/order")
    public ResponseEntity<OrderResponse> placeOrder(@RequestParam Long customerId,
            @RequestBody List<OrderItemReq> items) {

        validateCustomerAccess(customerId);

        OrderModel order = orderService.createOrder(customerId, items);
        return ResponseEntity.status(HttpStatus.CREATED).body(new OrderResponse(order));
    }

    // get order details by ID
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/orders/{id}")
    public ResponseEntity<OrderResponse> getOrderDetails(@PathVariable long id) {
        OrderModel order = orderService.orderDetails(id);
        validateCustomerAccess(order.getCustomer().getId());

        OrderResponse response = new OrderResponse(order);
        return ResponseEntity.ok(response);
    }

    // get all orders of a specific customer
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/orders/{customerId}/orders")
    public List<OrderResponse> getAllOrdersByCustomer(@PathVariable long customerId) {
        validateCustomerAccess(customerId);

        List<OrderModel> orders = orderService.AllOrdersBySingleCustomer(customerId);
        return orders.stream()
                .map(OrderResponse::new)
                .collect(Collectors.toList());
    }

    // delete an order by specific user
    @PreAuthorize("hasRole('USER')")
    @DeleteMapping("/order/{customerId}/{orderId}")
    public ResponseEntity<String> deleteOrder(@PathVariable long customerId, @PathVariable long orderId) {
        validateCustomerAccess(customerId);

        orderService.deleteOrder(customerId, orderId);
        return ResponseEntity.ok("Order deleted succesfully");
    }

    // get list of all orders
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin/allorders")
    public ResponseEntity<List<OrderModel>> getOrderList() {
        List<OrderModel> orders = orderService.getOrderList();
        if (orders.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(orders);
    }

    private void validateCustomerAccess(Long customerId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName(); // logged in user

        // method to fetch customer ID by username
        Long loggedInCustomerId = orderService.getCustomerIdByUsername(username);

        if (!customerId.equals(loggedInCustomerId)) {
            throw new SecurityException("Unauthorized access: You can only access your own orders.");
        }
    }
}
