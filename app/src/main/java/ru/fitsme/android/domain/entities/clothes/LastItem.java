package ru.fitsme.android.domain.entities.clothes;

public class LastItem {
    private int page;
    private int index;

    public LastItem(int page, int index) {
        this.page = page;
        this.index = index;
    }

    public int getPage() {
        return page;
    }

    public int getIndex() {
        return index;
    }
}
