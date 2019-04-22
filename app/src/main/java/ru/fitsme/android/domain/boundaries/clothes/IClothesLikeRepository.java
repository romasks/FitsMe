package ru.fitsme.android.domain.boundaries.clothes;

import android.support.annotation.NonNull;

import ru.fitsme.android.data.entities.exceptions.AppException;

public interface IClothesLikeRepository {
    void likeItem(@NonNull String token, int id, boolean liked) throws AppException;
}
