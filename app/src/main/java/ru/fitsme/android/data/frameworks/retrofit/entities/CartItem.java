package ru.fitsme.android.data.frameworks.retrofit.entities;

public class CartItem {
    private int clotheId;
    private int quantity;

    public CartItem(int clotheId, int quantity) {
        this.clotheId = clotheId;
        this.quantity = quantity;
    }

    public int getClotheId() {
        return clotheId;
    }

    public int getQuantity() {
        return quantity;
    }
}
