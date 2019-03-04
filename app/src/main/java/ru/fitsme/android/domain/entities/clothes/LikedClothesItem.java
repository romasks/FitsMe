package ru.fitsme.android.domain.entities.clothes;

public class LikedClothesItem {
    private int id;
    private boolean liked;
    private String add_datetime;
    private ClothesItem clothe;

    public int getId() {
        return id;
    }

    public boolean isLiked() {
        return liked;
    }

    public String getAddDatetime() {
        return add_datetime;
    }

    public ClothesItem getClothe() {
        return clothe;
    }
}
