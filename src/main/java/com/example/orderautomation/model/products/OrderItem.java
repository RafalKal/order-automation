package com.example.orderautomation.model.products;

public class OrderItem {

    private String product;
    boolean requireFullAmount = true;
    private int count;

    public OrderItem(String id, int count) {
        this.product = id;
        this.count = count;
    }

    public boolean isRequireFullAmount() {
        return requireFullAmount;
    }

    public void setRequireFullAmount(boolean requireFullAmount) {
        this.requireFullAmount = requireFullAmount;
    }

    public String getId() {
        return product;
    }

    public void setId(String id) {
        this.product = id;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
