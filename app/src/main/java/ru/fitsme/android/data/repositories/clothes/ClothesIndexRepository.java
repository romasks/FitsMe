package ru.fitsme.android.data.repositories.clothes;

import javax.inject.Inject;

import ru.fitsme.android.data.frameworks.sharedpreferences.ClothesIndexStorage;
import ru.fitsme.android.domain.boundaries.clothes.IClothesIndexRepository;
import ru.fitsme.android.domain.entities.exceptions.internal.DataNotFoundException;

public class ClothesIndexRepository implements IClothesIndexRepository {

    private final ClothesIndexStorage clothesIndexStorage;

    @Inject
    ClothesIndexRepository(ClothesIndexStorage clothesIndexStorage) {
        this.clothesIndexStorage = clothesIndexStorage;
    }

    @Override
    public int getLastClothesItemIndex() {
        try {
            return clothesIndexStorage.getData();
        } catch (DataNotFoundException e) {
            return 0;
        }
    }

    @Override
    public void setLastClothesItemIndex(int index) {
        clothesIndexStorage.setData(index);
    }
}
