package ru.fitsme.android.domain.entities.clothes;

import ru.fitsme.android.data.frameworks.room.RoomColor;

public class FilterColor implements ClotheFilter {

    private int id;
    private String colorName;
    private String colorHex;
    private boolean isChecked;

    public FilterColor(RoomColor roomColor){
        this.id = roomColor.getId();
        this.colorName = roomColor.getColorName();
        this.colorHex = roomColor.getColorHex();
        this.isChecked = roomColor.isChecked();
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
        return colorName;
    }

    public void setTitle(String colorName) {
        this.colorName = colorName;
    }

    @Override
    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public String getColorHex() {
        return colorHex;
    }

    public void setColorHex(String colorHex) {
        this.colorHex = colorHex;
    }
}
