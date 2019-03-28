package ru.fitsme.android.domain.interactors.cart;

import android.support.annotation.NonNull;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;
import ru.fitsme.android.domain.entities.favourites.FavouritesItem;

public interface ICartInteractor {

    @NonNull
    Single<FavouritesItem> getSingleCartItem(int index);

    @NonNull
    Single<List<FavouritesItem>> getSingleCartItems(int page);
}
