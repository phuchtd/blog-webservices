package com.example.blog.service.impl;

import com.example.blog.entity.Order;
import com.example.blog.entity.Payment;
import com.example.blog.exception.PaymentException;
import com.example.blog.payload.OrderRequest;
import com.example.blog.payload.OrderResponse;
import com.example.blog.repository.OrderRepository;
import com.example.blog.repository.PaymentRepository;
import com.example.blog.service.OrderService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class OrderServiceImpl implements OrderService {

    private OrderRepository orderRepository;
    private PaymentRepository paymentRepository;

    public OrderServiceImpl(OrderRepository orderRepository, PaymentRepository paymentRepository) {
        this.orderRepository = orderRepository;
        this.paymentRepository = paymentRepository;
    }

    @Override
    @Transactional
    public OrderResponse placeOrder(OrderRequest orderRequest) {

        Order order = orderRequest.getOrder();
        order.setStatus("IN_PROGRESS");
        order.setOrderTrackingNumber(UUID.randomUUID().toString());
        orderRepository.save(order);

        Payment payment = orderRequest.getPayment();
        if (!payment.getType().equals("DEBIT")) {
            throw new PaymentException("Payment card type does not support.");
        }
        payment.setOrderId(order.getId());
        paymentRepository.save(payment);

        OrderResponse orderResponse = new OrderResponse();
        orderResponse.setOrderTackingNumber(order.getOrderTrackingNumber());
        orderResponse.setStatus(order.getStatus());
        orderResponse.setMessage("SUCCESS");

        return orderResponse;
    }
}
