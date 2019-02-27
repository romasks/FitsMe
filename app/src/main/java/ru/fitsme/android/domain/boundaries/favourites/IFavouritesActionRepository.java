package ru.fitsme.android.domain.boundaries.favourites;

import android.support.annotation.NonNull;

public interface IFavouritesActionRepository {

    void removeItem(@NonNull String token, int id);

    void restoreItem(@NonNull String token, int id);

    void orderItem(@NonNull String token, int id);

}
