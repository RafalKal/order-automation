package com.example.orderautomation.model.products;

public class BaselinkerOrderProduct {
    String ean;
    String attributes;
    int quantity;

    public String getEan() {
        return ean;
    }

    public void setEan(String ean) {
        this.ean = ean;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getAttribute() {
        return attributes;
    }

    public void setAttribute(String attribute) {
        this.attributes = attribute;
    }
}
