package ru.fitsme.android.data.repositories.clothes.entity;

public class RepoClotheColor {
    private int id;
    private String colorName;
    private String colorHex;

    public RepoClotheColor(int id, String colorName, String colorHex) {
        this.id = id;
        this.colorName = colorName;
        this.colorHex = colorHex;
    }

    public String getColorName() {
        return colorName;
    }

    public void setColorName(String colorName) {
        this.colorName = colorName;
    }

    public String getColorHex() {
        return colorHex;
    }

    public void setColorHex(String colorHex) {
        this.colorHex = colorHex;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
