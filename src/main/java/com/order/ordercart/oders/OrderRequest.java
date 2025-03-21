package com.order.ordercart.oders;

import java.util.List;

import com.order.ordercart.orderitem.OrderItemReq;

import lombok.Data;

@Data
public class OrderRequest {
    private Long customerId;
    private List<OrderItemReq> items;
}
