package com.order.ordercart.oders;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.order.ordercart.customer.CustomerModel;

@Repository
public interface OrderRepository extends JpaRepository<OrderModel, Long> {

    // get all orders of a specific customer
    List<OrderModel> findByCustomer(CustomerModel customer);
}
