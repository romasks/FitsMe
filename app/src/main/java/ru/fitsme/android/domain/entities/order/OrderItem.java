package ru.fitsme.android.domain.entities.order;

import ru.fitsme.android.domain.entities.clothes.ClothesItem;

public class OrderItem {
    private int id;
    private int order;
    private int price;
    private int quantity;
    private ClothesItem clothe;

    public int getId() {
        return id;
    }

    public int getOrder() {
        return order;
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
