package com.example.order_service.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class OrderResponse {
    private Long orderId;
    private Long productId;
    private int quantity;
    private Product product;

    public OrderResponse(Long orderId, Long productId, int quantity, Product product) {
        this.orderId = orderId;
        this.productId = productId;
        this.quantity = quantity;
        this.product = product;
    }

    public Long getOrderId() { return orderId; }
    public void setOrderId(Long orderId) { this.orderId = orderId; }

    public Long getProductId() { return productId; }
    public void setProductId(Long productId) { this.productId = productId; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public Product getProduct() { return product; }
    public void setProduct(Product product) { this.product = product; }
}
