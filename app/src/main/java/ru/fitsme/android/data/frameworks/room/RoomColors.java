package ru.fitsme.android.data.frameworks.room;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class RoomColors {

    @PrimaryKey
    private int id;
    private String colorName;

    public RoomColors(int id, String colorName){
        this.id = id;
        this.colorName = colorName;
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
}
