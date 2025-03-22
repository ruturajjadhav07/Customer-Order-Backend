package com.order.ordercart.orderitem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order-items")
public class OrderItemController {

    @Autowired
    private OrderItemService orderItemService;

    // fetch order details and product name by user id
    @GetMapping("/{customer_id}/{order_id}/{product_id}")
    public ResponseEntity<OrderItem> fetchOrder(
            @PathVariable long customer_id,
            @PathVariable long order_id,
            @PathVariable long product_id) {

        OrderItem orderItem = orderItemService.fetchOrder(customer_id, order_id, product_id);
        return ResponseEntity.ok(orderItem);
    }
}
