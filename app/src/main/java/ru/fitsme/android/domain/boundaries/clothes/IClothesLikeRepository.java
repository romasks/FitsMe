package ru.fitsme.android.domain.boundaries.clothes;

import android.support.annotation.NonNull;

public interface IClothesLikeRepository {
    void likeItem(@NonNull String token, int id, boolean liked);
}
