package ru.fitsme.android.data.entities.response.clothes;

public class LastItem {

    private int page;
    private int index;

    public LastItem(int page, int index) {
        this.page = page;
        this.index = index;
    }

    public LastItem() {
        this(1, 0);
    }

    public int getPage() {
        return page;
    }

    public int getIndex() {
        return index;
    }
}
