package ru.fitsme.android.data.repositories.clothes;

import android.support.annotation.NonNull;
import android.util.SparseArray;

import javax.inject.Inject;

import ru.fitsme.android.data.frameworks.retrofit.WebLoader;
import ru.fitsme.android.domain.boundaries.clothes.IClothesRepository;
import ru.fitsme.android.domain.entities.clothes.ClothesItem;
import ru.fitsme.android.domain.entities.clothes.ClothesPage;
import ru.fitsme.android.domain.entities.exceptions.AppException;

public class ClothesRepository implements IClothesRepository {

    private static final int PAGE_SIZE = 10;

    private final SparseArray<ClothesPage> clothesPageSparseArray = new SparseArray<>();
    private final WebLoader webLoader;

    @Inject
    public ClothesRepository(WebLoader webLoader) {
        this.webLoader = webLoader;
    }

    @NonNull
    @Override
    public ClothesItem getClothesItem(int index) throws AppException {
        int pageIndex = calculatePageIndex(index);
        ClothesPage clothesPage = clothesPageSparseArray.get(pageIndex);
        if (clothesPage == null) {
            //clothesPage = webLoader.
        }
        return null;
    }

    private int calculatePageIndex(int index) {
        return index / PAGE_SIZE;
    }
}
