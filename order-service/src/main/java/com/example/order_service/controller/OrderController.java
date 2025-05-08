package com.example.order_service.controller;

import com.example.order_service.dto.OrderResponse;
import com.example.order_service.dto.Product;
import com.example.order_service.model.Order;
import com.example.order_service.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private RestTemplate restTemplate;

    private final String PRODUCT_SERVICE_URL = "http://localhost:8081/products";

    @PostMapping
    public String placeOrder(@RequestBody Order order) {
        Product product = restTemplate.getForObject(PRODUCT_SERVICE_URL + "/" + order.getProductId(), Product.class);

        if (product == null) {
            return "Product not found. Cannot place order.";
        }

        orderRepository.save(order);
        return "Order placed successfully for product: " + product.getName();
    }

    @GetMapping("/{id}")
    public OrderResponse getOrderWithProduct(@PathVariable Long id) {
        Order order = orderRepository.findById(id).orElse(null);

        if (order == null) {
            return null;
        }

        Product product = restTemplate.getForObject(PRODUCT_SERVICE_URL + "/" + order.getProductId(), Product.class);

        return new OrderResponse(order.getId(), order.getProductId(), order.getQuantity(), product);
    }

    @GetMapping
    public List<OrderResponse> getAllOrdersWithProduct() {
        List<Order> orders = orderRepository.findAll();

        return orders.stream().map(order -> {
            Product product = restTemplate.getForObject(PRODUCT_SERVICE_URL + "/" + order.getProductId(), Product.class);
            return new OrderResponse(order.getId(), order.getProductId(), order.getQuantity(), product);
        }).toList();
    }
}
