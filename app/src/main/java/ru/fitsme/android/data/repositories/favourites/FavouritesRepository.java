package ru.fitsme.android.data.repositories.favourites;

import android.support.annotation.NonNull;

import javax.inject.Inject;

import ru.fitsme.android.data.frameworks.retrofit.WebLoader;
import ru.fitsme.android.data.repositories.favourites.entity.FavouritesPage;
import ru.fitsme.android.domain.boundaries.favourites.IFavouritesRepository;
import ru.fitsme.android.domain.entities.exceptions.AppException;
import ru.fitsme.android.domain.entities.favourites.FavouritesItem;

public class FavouritesRepository implements IFavouritesRepository {
    private final WebLoader webLoader;

    @Inject
    public FavouritesRepository(WebLoader webLoader) {
        this.webLoader = webLoader;
    }

    @NonNull
    @Override
    public FavouritesItem getFavouritesItem(@NonNull String token, int index) {
        return new FavouritesItem();
    }

    @NonNull
    @Override
    public FavouritesPage getFavouritesPage(@NonNull String token, int page) throws AppException {
        return webLoader.getFavouritesClothesPage(token, page);
    }
}
