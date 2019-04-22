package ru.fitsme.android.domain.boundaries.favourites;

import android.support.annotation.NonNull;

import ru.fitsme.android.data.entities.response.favourites.FavouritesPage;
import ru.fitsme.android.data.entities.exceptions.AppException;
import ru.fitsme.android.data.entities.response.favourites.FavouritesItem;

public interface IFavouritesRepository {

    String TAG = "FavouritesRepository";

    @NonNull
    FavouritesItem getFavouritesItem(@NonNull String token, int index) throws AppException;

    @NonNull
    FavouritesPage getFavouritesPage(@NonNull String token, int page) throws AppException;
}
