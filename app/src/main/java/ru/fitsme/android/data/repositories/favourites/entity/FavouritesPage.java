package ru.fitsme.android.data.repositories.favourites.entity;

import java.util.List;

import ru.fitsme.android.domain.entities.favourites.FavouritesItem;

public class FavouritesPage {
    private long count;
    private int next;
    private int previous;
    private List<FavouritesItem> items;

    public long getCount() {
        return count;
    }

    public int getNext() {
        return next;
    }

    public int getPrevious() {
        return previous;
    }

    public List<FavouritesItem> getItems() {
        return items;
    }
}
