package ru.fitsme.android.domain.entities.clothes;

import ru.fitsme.android.data.frameworks.room.RoomBrand;

public class FilterBrand implements ClotheFilter{

    private int id;
    private String title;
    private boolean isChecked;

    public FilterBrand(RoomBrand roomBrand){
        this.id = roomBrand.getId();
        this.title = roomBrand.getTitle();
        this.isChecked = roomBrand.isChecked();
    }

    @Override
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}
