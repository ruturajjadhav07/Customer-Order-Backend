package com.order.ordercart.oders;

import lombok.Data;
import java.sql.Date;

import com.order.ordercart.customer.CustomerResponse;

@Data
public class OrderResponse {
    private Long orderId;
    private String orderName;
    private Date orderDate;
    private double orderAmount;
    private CustomerResponse customer;

    public OrderResponse(OrderModel order) {
        this.orderId = order.getOrderId();
        this.orderName = order.getOrderName();
        this.orderDate = order.getOrderDate();
        this.orderAmount = order.getOrderAmount();
        this.customer = new CustomerResponse(order.getCustomer());
    }
}
