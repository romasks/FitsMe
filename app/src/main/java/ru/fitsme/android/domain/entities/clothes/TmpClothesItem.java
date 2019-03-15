package ru.fitsme.android.domain.entities.clothes;

public class TmpClothesItem extends ClothesItem {
    private String brand = "Название бренда \nв две строчки";
    private String name = "Название товара";
    private String description = "7000 P";

    public String getBrand() {
        return brand;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
