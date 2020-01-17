package ru.fitsme.android.data.frameworks.room;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class RoomColor {

    @PrimaryKey
    private int id;
    private String colorName;
    private String colorHex;
    private boolean isChecked;
    private boolean isUpdated;

    public RoomColor(int id, String colorName, String colorHex, boolean isChecked, boolean isUpdated){
        this.id = id;
        this.colorName = colorName;
        this.colorHex = colorHex;
        this.isChecked = isChecked;
        this.isUpdated = isUpdated;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getColorName() {
        return colorName;
    }

    public void setColorName(String colorName) {
        this.colorName = colorName;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public boolean isUpdated() {
        return isUpdated;
    }

    public void setUpdated(boolean updated) {
        isUpdated = updated;
    }

    public String getColorHex() {
        return colorHex;
    }

    public void setColorHex(String colorHex) {
        this.colorHex = colorHex;
    }
}
