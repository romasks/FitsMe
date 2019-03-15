package ru.fitsme.android.domain.boundaries.favourites;

import android.support.annotation.NonNull;

import java.util.List;

import ru.fitsme.android.domain.entities.clothes.ClothesItem;
import ru.fitsme.android.domain.entities.exceptions.AppException;

public interface IFavouritesRepository {

    @NonNull
    ClothesItem getFavouritesItem(@NonNull String token, int index) throws AppException;

    @NonNull
    List<ClothesItem> getFavouritesPage(@NonNull String token, int index, int count) throws AppException;
}
