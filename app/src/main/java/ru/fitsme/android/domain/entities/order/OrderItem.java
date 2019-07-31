package ru.fitsme.android.domain.entities.order;

import com.google.gson.annotations.SerializedName;

import ru.fitsme.android.domain.entities.clothes.ClothesItem;

public class OrderItem {
    @SerializedName("id")
    private int id;

    @SerializedName("order")
    private int orderId;

    @SerializedName("price")
    private int price;

    private int quantity;

    @SerializedName("clothe")
    private ClothesItem clothe;

    public int getId() {
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public int getOrderId() {
        return orderId;
    }

    public int getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public ClothesItem getClothe() {
        return clothe;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OrderItem)) return false;
        OrderItem that = (OrderItem) o;
        return getId() == that.getId() &&
                getOrderId() == that.getOrderId() &&
                getPrice() == that.getPrice() &&
                getQuantity() == that.getQuantity() &&
                getClothe() == that.getClothe();
    }

    @Override
    public int hashCode() {
        int result = 17;
        int prime = 31;
        result = prime * result + getId();
        result = prime * result + getOrderId();
        result = prime * result + getPrice();
        result = prime * result + getQuantity();
        result = prime * result + getClothe().hashCode();
        return result;
    }
}
