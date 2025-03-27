package com.order.ordercart.oders;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.order.ordercart.customer.CustomerModel;
import com.order.ordercart.customer.CustomerRepository;
import com.order.ordercart.exception.CustomerNotFoundException;
import com.order.ordercart.exception.OrderCreationException;
import com.order.ordercart.exception.OrderItemNotFoundException;
import com.order.ordercart.exception.ProductNotFoundException;
import com.order.ordercart.exception.StockUnavailableException;
import com.order.ordercart.orderitem.OrderItem;
import com.order.ordercart.orderitem.OrderItemRepository;
import com.order.ordercart.orderitem.OrderItemReq;
import com.order.ordercart.product.ProductModel;
import com.order.ordercart.product.ProductRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    // create a order
    public OrderModel createOrder(long customerId, List<OrderItemReq> items) {
        if (items == null || items.isEmpty()) {
            throw new OrderCreationException("Order cannot be empty. Please add items.");
        }

        CustomerModel customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found with id: " + customerId));

        OrderModel order = new OrderModel();
        order.setCustomer(customer);
        order.setOrderName("Order-" + customerId + "-" + System.currentTimeMillis());
        order.setOrderDate(Date.valueOf(LocalDate.now()));
        order.setOrderAmount(0.0);
        order = orderRepository.save(order);

        double totalAmount = 0.0;

        for (OrderItemReq item : items) {
            ProductModel product = productRepository.findById(item.getProductId())
                    .orElseThrow(
                            () -> new ProductNotFoundException("Product not found with id: " + item.getProductId()));

            if (product.getProductQuantity() < item.getQuantity()) {
                throw new StockUnavailableException("Insufficient stock for product: " + product.getProductName());
            }

            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setProduct(product);
            orderItem.setItemQuantity(item.getQuantity());
            orderItem.setItemAmount(product.getProductPrice() * item.getQuantity());
            orderItemRepository.save(orderItem);

            totalAmount += orderItem.getItemAmount();

            // Reduce product stock
            product.setProductQuantity(product.getProductQuantity() - item.getQuantity());
            productRepository.save(product);
        }

        order.setOrderAmount(totalAmount);
        return orderRepository.save(order);
    }

    // get order details by ID
    public OrderModel orderDetails(long order_id) {
        OrderModel getDetails = orderRepository.findById(order_id)
                .orElseThrow(() -> new OrderItemNotFoundException("Order details not found with id:" + order_id));
        return getDetails;
    }

    // get all orders of a specific customer
    public List<OrderModel> AllOrdersBySingleCustomer(long id) {
        CustomerModel customer = customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found with id: " + id));
        return orderRepository.findByCustomer(customer);
    }

    // delete an order by specific user
    public void deleteOrder(long id, long order_id) {
        @SuppressWarnings("unused")
        CustomerModel customer = customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found with id: " + id));

        OrderModel order = orderRepository.findById(order_id).filter(o -> o.getCustomer().getId() == id)
                .orElseThrow(() -> new OrderItemNotFoundException("Order does not belong to customer "));

        orderItemRepository.deleteByOrder(order);

        orderRepository.deleteById(order_id);
    }

    // get list of all orders
    public List<OrderModel> getOrderList() {
        return orderRepository.findAll();
    }

    // helper method
    public Long getCustomerIdByUsername(String username) {
        return customerRepository.findByEmail(username)
                .map(customer -> customer.getId()) // Extracting customer ID
                .orElseThrow(() -> new RuntimeException("Customer not found with username: " + username));
    }

}
