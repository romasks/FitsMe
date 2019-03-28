package ru.fitsme.android.domain.boundaries.favourites;

import android.support.annotation.NonNull;

import ru.fitsme.android.domain.entities.exceptions.user.UserException;

public interface IFavouritesActionRepository {

    void removeItem(@NonNull String token, int id);

    void restoreItem(@NonNull String token, int id);

    void addItemToCart(@NonNull String token, int id, int quantity) throws UserException;

}
