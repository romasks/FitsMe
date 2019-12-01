package ru.fitsme.android.data.frameworks.retrofit.entities;

public class ReturnsItemRequest {
    private int orderitems_id;
    private int quantity;

    public ReturnsItemRequest(int orderitems_id, int quantity) {
        this.orderitems_id = orderitems_id;
        this.quantity = quantity;
    }

    public int getOrderitemsId() {
        return orderitems_id;
    }

    public int getQuantity() {
        return quantity;
    }
}
