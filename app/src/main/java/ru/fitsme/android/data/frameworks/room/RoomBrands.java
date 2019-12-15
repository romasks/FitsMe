package ru.fitsme.android.data.frameworks.room;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class RoomBrands {

    @PrimaryKey
    private int id;
    private String title;

    public RoomBrands(int id, String title){
        this.id = id;
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
