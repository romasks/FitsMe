package ru.fitsme.android.data.frameworks.room;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import ru.fitsme.android.data.repositories.clothes.entity.RepoClotheBrand;
import ru.fitsme.android.domain.entities.clothes.FilterBrand;

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

    public RoomBrand(FilterBrand brand, boolean isUpdated) {
        this.id = brand.getId();
        this.title = brand.getColorName();
        this.isChecked = brand.isChecked();
        this.isUpdated = isUpdated;
    }

    public RoomBrand(RoomBrand brand, boolean isUpdated) {
        this.id = brand.getId();
        this.title = brand.getTitle();
        this.isChecked = brand.isChecked();
        this.isUpdated = isUpdated;
    }

    public RoomBrand(RepoClotheBrand brand, boolean isChecked, boolean isUpdated) {
        this.id = brand.getId();
        this.title = brand.getTitle();
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
