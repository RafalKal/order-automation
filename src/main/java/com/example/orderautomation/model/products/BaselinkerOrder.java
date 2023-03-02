package com.example.orderautomation.model.products;

import java.util.List;

public class BaselinkerOrder {

    private int order_status_id;
    private List<BaselinkerOrderProduct> products;

    public List<BaselinkerOrderProduct> getProducts() {
        return products;
    }

    public void setProducts(List<BaselinkerOrderProduct> products) {
        this.products = products;
    }

    public int getOrder_status_id() {
        return order_status_id;
    }

    public void setOrder_status_id(int order_status_id) {
        this.order_status_id = order_status_id;
    }
}
