package ru.fitsme.android.data.entities.response.clothes;

public class LikedClothesItem {
    private int id;
    private boolean liked;
    private boolean in_cart;
    private String add_datetime;
    private ClothesItem clothe;

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
