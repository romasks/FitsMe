package ru.fitsme.android.data.models;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import ru.fitsme.android.BR;
import ru.fitsme.android.domain.entities.clothes.ClothesItem;

public class ClothesItemModel extends BaseObservable {

    private int id;
    private String brandName;
    private String name;
    private String description;
    private String imageUrl;

    public ClothesItemModel(ClothesItem clothesItem) {
        this.id = clothesItem.getId();
        this.brandName = clothesItem.getBrand();
        this.name = clothesItem.getName();
        this.description = clothesItem.getDescription();
        this.imageUrl = clothesItem.getPics().get(0).getUrl()
                .replace("random", "image=") + (id % 400);
    }

    @Bindable
    public int getId() {
        return id;
    }

    @Bindable
    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
        notifyPropertyChanged(BR.brandName);
    }

    @Bindable
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        notifyPropertyChanged(BR.name);
    }

    @Bindable
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
        notifyPropertyChanged(BR.description);
    }

    @Bindable
    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        notifyPropertyChanged(BR.imageUrl);
    }

}
