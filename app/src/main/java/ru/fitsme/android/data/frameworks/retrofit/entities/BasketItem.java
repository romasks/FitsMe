package ru.fitsme.android.data.frameworks.retrofit.entities;

public class BasketItem {
    private int clothe_id;
    private int quantity;

    public BasketItem(int clothe_id, int quantity) {
        this.clothe_id = clothe_id;
        this.quantity = quantity;
    }

    public int getClotheId() {
        return clothe_id;
    }

    public int isLiked() {
        return quantity;
    }
}
