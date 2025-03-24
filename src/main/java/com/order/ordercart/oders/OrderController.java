package com.order.ordercart.oders;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.order.ordercart.orderitem.OrderItemReq;

@RestController
public class OrderController {

    @Autowired
    private OrderService orderService;

    // create a order
    @PostMapping("/login/order")
    public ResponseEntity<OrderResponse> placeOrder(@RequestParam Long customerId,
            @RequestBody List<OrderItemReq> items) {
        OrderModel order = orderService.createOrder(customerId, items);
        return ResponseEntity.status(HttpStatus.CREATED).body(new OrderResponse(order));
    }

    // get order details by ID
    @GetMapping("/allorders/{id}")
    public ResponseEntity<OrderResponse> getOrderDetails(@PathVariable long id) {
        OrderModel order = orderService.orderDetails(id);
        OrderResponse response = new OrderResponse(order);
        return ResponseEntity.ok(response);
    }

    // get all orders of a specific customer
    @GetMapping("/allorders/{id}/orders")
    public List<OrderResponse> getAllOrdersByCustomer(@PathVariable long id) {
        List<OrderModel> orders = orderService.AllOrdersBySingleCustomer(id);
        return orders.stream()
                .map(OrderResponse::new)
                .collect(Collectors.toList());
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
}
