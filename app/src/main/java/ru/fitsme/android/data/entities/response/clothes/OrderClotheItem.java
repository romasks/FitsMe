package ru.fitsme.android.data.entities.response.clothes;

public class OrderClotheItem {
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
