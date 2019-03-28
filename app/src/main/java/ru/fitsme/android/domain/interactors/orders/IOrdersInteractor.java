package ru.fitsme.android.domain.interactors.orders;

import android.support.annotation.NonNull;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;
import ru.fitsme.android.domain.entities.favourites.FavouritesItem;
import ru.fitsme.android.domain.entities.order.OrderItem;

public interface IOrdersInteractor {

    @NonNull
    Single<FavouritesItem> getSingleCartItem(int index);

    @NonNull
    Single<List<FavouritesItem>> getSingleCartItems(int page);

    @NonNull
    Single<OrderItem> getSingleOrderItem(int index);

    @NonNull
    Completable makeOrder(OrderItem order);
}
