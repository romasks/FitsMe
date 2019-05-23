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
}
