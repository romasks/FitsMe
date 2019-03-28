package ru.fitsme.android.domain.entities.favourites;

import java.util.Date;

import ru.fitsme.android.domain.entities.clothes.ClothesItem;

public class FavouritesItem {
    private int id;
    private ClothesItem clothe;
    private Date add_datetime;
    private boolean liked;
    private boolean inCart;

    public int getId() {
        return id;
    }

    public ClothesItem getItem() {
        return clothe;
    }

    public Date getAddDatetime() {
        return add_datetime;
    }

    public boolean isLiked() {
        return liked;
    }

    public boolean isInCart() {
        return inCart;
    }
}
