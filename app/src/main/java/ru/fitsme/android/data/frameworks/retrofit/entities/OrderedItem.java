package ru.fitsme.android.data.frameworks.retrofit.entities;

public class OrderedItem {
    private int clothe_id;
    private int quantity;

    public OrderedItem(int clothe_id, int quantity) {
        this.clothe_id = clothe_id;
        this.quantity = quantity;
    }

    public int getClothe_id() {
        return clothe_id;
    }

    public int getQuantity() {
        return quantity;
    }
}
