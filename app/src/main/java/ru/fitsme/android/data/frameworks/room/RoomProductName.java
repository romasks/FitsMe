package ru.fitsme.android.data.frameworks.room;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class RoomProductName {

    @PrimaryKey
    private int id;
    private String title;
    private String type;
    private boolean isChecked;
    private boolean isUpdated;

    public RoomProductName(int id, String title, String type, boolean isChecked, boolean isUpdated){
        this.id = id;
        this.title = title;
        this.type = type;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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
