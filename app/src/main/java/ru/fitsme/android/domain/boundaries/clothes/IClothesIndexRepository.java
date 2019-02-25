package ru.fitsme.android.domain.boundaries.clothes;

public interface IClothesIndexRepository {

    int getLastClothesItemIndex();

    void setLastClothesItemIndex(int index);
}
