package ru.fitsme.android.domain.entities.favourites;

import java.util.Date;
import java.util.Objects;

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

    public void setInCart(boolean inCart) {
        this.inCart = inCart;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FavouritesItem)) return false;
        FavouritesItem that = (FavouritesItem) o;
        return getId() == that.getId() &&
                isLiked() == that.isLiked() &&
                isInCart() == that.isInCart() &&
                getItem().equals(that.clothe) &&
                getAddDatetime().equals(that.add_datetime);
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + getId();
        result = 31 * result + (isLiked() ? 1231 : 1237);
        result = 31 * result + (isInCart() ? 1231 : 1237);
        result = 31 * result + getItem().hashCode();
        result = 31 * result + getAddDatetime().hashCode();
        return result;
    }
}
