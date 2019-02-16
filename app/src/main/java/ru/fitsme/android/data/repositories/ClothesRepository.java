package ru.fitsme.android.data.repositories;

import android.support.annotation.NonNull;

import javax.inject.Inject;

import ru.fitsme.android.data.frameworks.retrofit.WebLoader;
import ru.fitsme.android.data.frameworks.sharedpreferences.LastPageStorage;
import ru.fitsme.android.domain.boundaries.signinup.IClothesRepository;
import ru.fitsme.android.domain.entities.clothes.ClothesItem;
import ru.fitsme.android.domain.entities.clothes.ClothesPage;
import ru.fitsme.android.domain.entities.clothes.LastItem;
import ru.fitsme.android.domain.entities.exceptions.AppException;
import ru.fitsme.android.domain.entities.exceptions.internal.DataNotFoundException;

public class ClothesRepository implements IClothesRepository {

    private WebLoader webLoader;
    private LastPageStorage lastPageStorage;

    @Inject
    public ClothesRepository(WebLoader webLoader, LastPageStorage lastPageStorage) {
        this.webLoader = webLoader;
        this.lastPageStorage = lastPageStorage;
    }

    @Override
    public LastItem getLastPage() {
        try {
            return lastPageStorage.getData();
        } catch (DataNotFoundException e) {
            return new LastItem();
        }
    }

    @NonNull
    @Override
    public ClothesPage loadNextPage(int page) throws AppException {
        return webLoader.getClothesPage(page);
    }

    @Override
    public void rateItem(@NonNull ClothesItem clothesItem, boolean like) {
        //TODO:
    }

    @Override
    public void setLastItem(int page) {
        lastPageStorage.setData(new LastItem(page, 0));
    }
}
