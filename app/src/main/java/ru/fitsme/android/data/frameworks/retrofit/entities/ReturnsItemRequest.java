package ru.fitsme.android.data.frameworks.retrofit.entities;

public class ReturnsItemRequest {
    private int orderItemId;
    private int quantity;

    public ReturnsItemRequest(int orderItemId, int quantity) {
        this.orderItemId = orderItemId;
        this.quantity = quantity;
    }

    public int getOrderItemId() {
        return orderItemId;
    }

    public int getQuantity() {
        return quantity;
    }
}
