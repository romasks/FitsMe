package ru.fitsme.android.domain.entities.clothes;

public class BasketClotheItem {
    private int id;
    private int orderId;
    private ClothesItem clothe;

    public int getId() {
        return id;
    }

    public int getOrderId() {
        return orderId;
    }

    public ClothesItem getClothe() {
        return clothe;
    }
}
