package com.example.orderautomation.model.order;

public class EtradeOrderProduct {

    private String id;
    private boolean requireFullAmount = true;
    int count;


    @Override
    public String toString() {
        return "EtradeProduct{" +
                "Kod Impakt=" + id +
                ", requireFullAmount=" + requireFullAmount +
                ", count=" + count +
                '}';
    }

    public EtradeOrderProduct() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isRequireFullAmount() {
        return requireFullAmount;
    }

    public void setRequireFullAmount(boolean requireFullAmount) {
        this.requireFullAmount = requireFullAmount;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
