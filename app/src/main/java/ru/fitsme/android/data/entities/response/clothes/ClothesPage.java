package ru.fitsme.android.data.entities.response.clothes;

import java.util.List;

public class ClothesPage {
    private long count;
    private int next;
    private int previous;
    private List<ClothesItem> items;

    public long getCount() {
        return count;
    }

    public int getNext() {
        return next;
    }

    public int getPrevious() {
        return previous;
    }

    public List<ClothesItem> getItems() {
        return items;
    }
}
