package com.example.blog.service;

import com.example.blog.payload.OrderRequest;
import com.example.blog.payload.OrderResponse;

public interface OrderService {
    OrderResponse placeOrder(OrderRequest orderRequest);
}
