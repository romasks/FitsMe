package ru.fitsme.android.data.repositories.favourites;

import android.support.annotation.NonNull;

import ru.fitsme.android.domain.boundaries.favourites.IFavouritesRepository;
import ru.fitsme.android.domain.entities.clothes.ClothesItem;
import ru.fitsme.android.domain.entities.exceptions.AppException;

public class FavouritesRepository implements IFavouritesRepository {
    @NonNull
    @Override
    public ClothesItem getFavouritesItem(@NonNull String token, int index) throws AppException {
        return null;
    }
}
