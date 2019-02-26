package ru.fitsme.android.domain.entities.clothes;

import java.util.List;

public class ClothesItem {
    private int id;
    private String brand;
    private String name;
    private String description;
    private List<String> material;
    private List<Picture> pics;


    public int getId() {
        return id;
    }

    public String getBrand() {
        return brand;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public List<String> getMaterial() {
        return material;
    }

    public List<Picture> getPics() {
        return pics;
    }
}
