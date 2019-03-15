package ru.fitsme.android.domain.boundaries.favourites;

import android.support.annotation.NonNull;

import ru.fitsme.android.data.repositories.clothes.entity.ClothesPage;
import ru.fitsme.android.domain.entities.clothes.ClothesItem;
import ru.fitsme.android.domain.entities.exceptions.AppException;

public interface IFavouritesRepository {

    @NonNull
    ClothesItem getFavouritesItem(@NonNull String token, int index) throws AppException;

    @NonNull
    ClothesPage getFavouritesPage(@NonNull String token, int page) throws AppException;
}
