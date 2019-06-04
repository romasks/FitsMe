package ru.fitsme.android.domain.boundaries.favourites;

import android.support.annotation.NonNull;

import ru.fitsme.android.domain.entities.exceptions.internal.DataNotFoundException;
import ru.fitsme.android.domain.entities.exceptions.user.UserException;

public interface IFavouritesActionRepository {

    String TAG = "FavouritesActionRepo";

    void removeItem(int id) throws UserException, DataNotFoundException;

    void restoreItem(@NonNull String token, int id);

    void addItemToCart(int id) throws UserException, DataNotFoundException;

}
