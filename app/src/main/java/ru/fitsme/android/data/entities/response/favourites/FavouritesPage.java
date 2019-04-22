package ru.fitsme.android.data.entities.response.favourites;

import java.util.List;

public class FavouritesPage {
    private int count;
    private int current;
    private Integer next;
    private Integer previous;
    private List<FavouritesItem> items;

    public int getCount() {
        return count;
    }

    public int getCurrent() {
        return current;
    }

    public Integer getNext() {
        return next;
    }

    public Integer getPrevious() {
        return previous;
    }

    public List<FavouritesItem> getItems() {
        return items;
    }
}
