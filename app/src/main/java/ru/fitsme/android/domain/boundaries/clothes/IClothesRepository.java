package ru.fitsme.android.domain.boundaries.clothes;

import android.support.annotation.NonNull;

import ru.fitsme.android.data.entities.response.clothes.ClothesItem;
import ru.fitsme.android.data.entities.exceptions.AppException;

public interface IClothesRepository {

    @NonNull
    ClothesItem getClothesItem(@NonNull String token, int index) throws AppException;
}
