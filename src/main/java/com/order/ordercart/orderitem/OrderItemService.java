package com.order.ordercart.orderitem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.order.ordercart.customer.CustomerModel;
import com.order.ordercart.customer.CustomerRepository;
import com.order.ordercart.exception.CustomerNotFoundException;
import com.order.ordercart.exception.OrderItemNotFoundException;
import com.order.ordercart.oders.OrderModel;
import com.order.ordercart.oders.OrderRepository;
import com.order.ordercart.product.ProductModel;
import com.order.ordercart.product.ProductRepository;

@Service
public class OrderItemService {

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    // fetch order details and product name by user id
    public OrderItem fetchOrder(long customer_id, long order_id, long product_id) {

        @SuppressWarnings("unused")
        CustomerModel customer = customerRepository.findById(customer_id)
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found with id: " + customer_id));

        OrderModel order = orderRepository.findById(order_id)
                .filter(o -> o.getCustomer().getId() == customer_id)
                .orElseThrow(() -> new IllegalArgumentException("Order not found with id: " + order_id));

        ProductModel product = productRepository.findById(product_id)
                .orElseThrow(() -> new IllegalArgumentException("product not found with id: " + product_id));

        OrderItem orderItem = orderItemRepository.findByOrderAndProduct(order, product)
                .orElseThrow(() -> new OrderItemNotFoundException("No order item found for given order and product"));

        return orderItem;
    }
}
