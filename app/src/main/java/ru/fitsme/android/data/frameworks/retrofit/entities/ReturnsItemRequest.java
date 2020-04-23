package ru.fitsme.android.data.frameworks.retrofit.entities;

import com.google.gson.annotations.SerializedName;

public class ReturnsItemRequest {

    @SerializedName("orderitems_id")
    private int orderItemId;
    @SerializedName("quantity")
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
