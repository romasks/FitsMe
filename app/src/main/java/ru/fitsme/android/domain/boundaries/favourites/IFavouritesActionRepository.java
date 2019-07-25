package ru.fitsme.android.domain.boundaries.favourites;

import android.support.annotation.NonNull;

import io.reactivex.Single;
import ru.fitsme.android.domain.entities.exceptions.internal.DataNotFoundException;
import ru.fitsme.android.domain.entities.exceptions.user.UserException;
import ru.fitsme.android.domain.entities.favourites.FavouritesItem;
import ru.fitsme.android.domain.entities.order.OrderItem;

public interface IFavouritesActionRepository {

    String TAG = "FavouritesActionRepo";

    Single<FavouritesItem> removeItem(FavouritesItem item);

    void restoreItem(@NonNull String token, int id);

    Single<OrderItem> addItemToCart(FavouritesItem id);

}
