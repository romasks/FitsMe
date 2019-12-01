package ru.fitsme.android.domain.entities.order;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ReturnsOrder {
    @SerializedName("id")
    private int id;

    @SerializedName("orderitems")
    private OrderItem orderItem;

    @SerializedName("returns")
    private int returns;

    @Expose
    private String indicationNumber = "";
    @Expose
    private String cardNumber = "";

    public int getId() {
        return id;
    }

    public OrderItem getOrderItem() {
        return orderItem;
    }

    public int getReturns() {
        return returns;
    }

    public ReturnsOrder() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ReturnsOrder)) return false;
        ReturnsOrder that = (ReturnsOrder) o;
        return getId() == that.getId() &&
                getOrderItem() == that.getOrderItem() &&
                getReturns() == that.getReturns();
    }

    @Override
    public int hashCode() {
        int result = 17;
        int prime = 31;
        result = prime * result + (int) getId();
        result = prime * result + getOrderItem().hashCode();
        result = prime * result + getReturns();
        return result;
    }
}
