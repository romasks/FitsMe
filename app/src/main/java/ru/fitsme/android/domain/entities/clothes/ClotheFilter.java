package ru.fitsme.android.domain.entities.clothes;

public interface ClotheFilter {
    int getId();

    String getColorName();

    boolean isChecked();

    void setChecked(boolean isChecked);
}
