package ru.fitsme.android.domain.entities.clothes;

import java.util.List;

public class ClothesItem {
    private int id;
    private String brand;
    private String name;
    private String description;
    private List<String> material;
    private String material_percentage;
    private List<Picture> pics;
    private List<Integer> available_sizes_id;
    private ClotheType clothe_type;
    private String size_in_stock;
    private int price;

    public ClothesItem() {
    }

    public ClothesItem(String brand, String name, int price) {
        this.brand = brand;
        this.name = name;
        this.price = price;
    }

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

    public String getMaterialPercentage() {
        return material_percentage;
    }

    public List<Picture> getPics() {
        return pics;
    }

    public List<Integer> getAvailableSizesId() {
        return available_sizes_id;
    }

    public ClotheType getClotheType() {
        return clothe_type;
    }

    public SizeInStock getSizeInStock() {
        switch (size_in_stock){
            case "UNDEFINED":{
                return SizeInStock.UNDEFINED;
            }
            case "NO":{
                return SizeInStock.NO;
            }
            case "YES":{
                return SizeInStock.YES;
            }
            default: {
                throw new IllegalStateException("Unknown ClotheItem SizeInStock state");
            }
        }
    }

    public int getPrice() {
        return price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ClothesItem)) return false;
        ClothesItem that = (ClothesItem) o;
        return getId() == that.getId() &&
                getBrand().equals(that.getBrand()) &&
                getName().equals(that.getName()) &&
                getDescription().equals(that.getDescription()) &&
                getMaterial().equals(that.getMaterial()) &&
                getMaterialPercentage().equals(that.getMaterialPercentage()) &&
                getPics().equals(that.getPics()) &&
                getAvailableSizesId().equals(that.getAvailableSizesId()) &&
                getClotheType().equals(that.getClotheType()) &&
                getSizeInStock().equals(that.getSizeInStock()) &&
                getPrice() == that.getPrice();
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + getId();
        result = 31 * result + getBrand().hashCode();
        result = 31 * result + getName().hashCode();
        result = 31 * result + getDescription().hashCode();
        result = 31 * result + getMaterial().hashCode();
        result = 31 * result + getMaterialPercentage().hashCode();
        result = 31 * result + getPics().hashCode();
        result = 31 * result + getAvailableSizesId().hashCode();
        result = 31 * result + getClotheType().hashCode();
        result = 31 * result + getSizeInStock().hashCode();
        result = 31 * result + getPrice();
        return result;
    }


    public enum SizeInStock {
        UNDEFINED, NO, YES
    }
}
