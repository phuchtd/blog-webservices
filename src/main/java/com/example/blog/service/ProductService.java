package com.example.blog.service;

import com.example.blog.entity.Product;

import java.util.List;

public interface ProductService {
    List<Product> searchProducts(String query);

    Product createProduct(Product product);
}
