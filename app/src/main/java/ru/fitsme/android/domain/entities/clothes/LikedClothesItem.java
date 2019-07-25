package ru.fitsme.android.domain.entities.clothes;

public class LikedClothesItem {
    private int id;
    private ClothesItem clothe;
    private String add_datetime;
    private boolean liked;
    private boolean in_cart;

    public int getId() {
        return id;
    }

    public boolean isLiked() {
        return liked;
    }

    public boolean isIn_cart() {
        return in_cart;
    }

    public String getAddDatetime() {
        return add_datetime;
    }

    public ClothesItem getClothe() {
        return clothe;
    }
}
