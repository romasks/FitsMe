package ru.fitsme.android.domain.entities.clothes;

import java.util.List;
import java.util.Objects;

public class ClothesItem {
    private int id;
    private String brand;
    private String name;
    private String description;
    private List<String> material;
    private List<Picture> pics;
// TODO: price ?

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
                getPics().equals(that.getPics());
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + getId();
        result = 31 * result + getBrand().hashCode();
        result = 31 * result + getName().hashCode();
        result = 31 * result + getDescription().hashCode();
        result = 31 * result + getMaterial().hashCode();
        result = 31 * result + getPics().hashCode();
        return result;
    }
}
