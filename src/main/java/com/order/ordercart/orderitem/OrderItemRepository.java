package com.order.ordercart.orderitem;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.order.ordercart.oders.OrderModel;
import com.order.ordercart.product.ProductModel;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

    // fetch order details and product name by user id
    Optional<OrderItem> findByOrderAndProduct(OrderModel order, ProductModel product);

}
