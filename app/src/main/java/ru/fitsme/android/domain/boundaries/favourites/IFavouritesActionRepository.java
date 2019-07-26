package ru.fitsme.android.domain.boundaries.favourites;

import io.reactivex.Single;
import ru.fitsme.android.domain.entities.favourites.FavouritesItem;
import ru.fitsme.android.domain.entities.order.OrderItem;

public interface IFavouritesActionRepository {

    String TAG = "FavouritesActionRepo";

    Single<FavouritesItem> removeItem(FavouritesItem item);

    Single<FavouritesItem> restoreItem(FavouritesItem item);

    Single<OrderItem> addItemToCart(FavouritesItem id);

}
