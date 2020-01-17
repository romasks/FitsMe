package ru.fitsme.android.domain.entities.clothes;

public interface ClotheFilter {
    int getId();

    String getTitle();

    boolean isChecked();

    void setChecked(boolean isChecked);
}
