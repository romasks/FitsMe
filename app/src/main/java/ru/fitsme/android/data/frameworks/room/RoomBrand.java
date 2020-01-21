package ru.fitsme.android.data.frameworks.room;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class RoomBrand {

    @PrimaryKey
    private int id;
    private String title;
    private boolean isChecked;
    private boolean isUpdated;

    public RoomBrand(int id, String title, boolean isChecked, boolean isUpdated){
        this.id = id;
        this.title = title;
        this.isChecked = isChecked;
        this.isUpdated = isUpdated;
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
}
