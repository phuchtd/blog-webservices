package com.example.blog.payload;

import com.example.blog.entity.Order;
import com.example.blog.entity.Payment;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderRequest {
    private Order order;
    private Payment payment;
}
