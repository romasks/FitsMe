package ru.fitsme.android.data.repositories.favourites;

import android.support.annotation.NonNull;

import javax.inject.Inject;

import ru.fitsme.android.data.frameworks.retrofit.WebLoader;
import ru.fitsme.android.data.repositories.clothes.entity.ClothesPage;
import ru.fitsme.android.domain.boundaries.favourites.IFavouritesRepository;
import ru.fitsme.android.domain.entities.clothes.ClothesItem;
import ru.fitsme.android.domain.entities.exceptions.AppException;

public class FavouritesRepository implements IFavouritesRepository {
    private final WebLoader webLoader;

    //ToDo @Inject
    public FavouritesRepository(WebLoader webLoader) {
        this.webLoader = webLoader;
    }

    @NonNull
    @Override
    public ClothesItem getFavouritesItem(@NonNull String token, int index) throws AppException {
        return null;
    }

    @NonNull
    @Override
    public ClothesPage getFavouritesItems(@NonNull String token, int page) throws AppException {
        ClothesPage favouritesPage = webLoader.getFavouritesClothesPage(token,page);

        return favouritesPage;
    }
}
