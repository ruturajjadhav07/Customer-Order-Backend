package com.order.ordercart.oders;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.order.ordercart.orderitem.OrderItemReq;

@RestController
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/login/order")
    public ResponseEntity<OrderResponse> placeOrder(@RequestParam Long customerId,
            @RequestBody List<OrderItemReq> items) {
        OrderModel order = orderService.createOrder(customerId, items);
        return ResponseEntity.status(HttpStatus.CREATED).body(new OrderResponse(order));
    }

}
