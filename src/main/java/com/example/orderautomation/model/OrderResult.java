package com.example.orderautomation.model;

public class OrderResult {
    private String orderId;

    public String getOrderId() {
        return orderId;
    }

    public OrderResult(String orderId) {
        this.orderId = orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
}
