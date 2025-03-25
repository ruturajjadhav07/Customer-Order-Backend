package com.order.ordercart.orderitem;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.order.ordercart.customer.CustomerModel;
import com.order.ordercart.oders.OrderModel;
import com.order.ordercart.product.ProductModel;

import jakarta.transaction.Transactional;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

    // fetch order details and product name by user id
    Optional<OrderItem> findByOrderAndProduct(OrderModel order, ProductModel product);

    @Modifying
    @Transactional
    @Query("UPDATE OrderItem oi SET oi.product.productId = :newProductId WHERE oi.product.productId = :oldProductId")
    void updateProductReference(@Param("oldProductId") long oldProductId, @Param("newProductId") long newProductId);

    // delete an order by specific user
    @Modifying
    @Query("DELETE FROM OrderItem oi WHERE oi.order = :order")
    void deleteByOrder(@Param("order") OrderModel order);

    // update quantity of an order item
    Optional<CustomerModel> findByProduct(ProductModel product);

}
