package ru.fitsme.android.domain.interactors.favourites;

import android.support.annotation.NonNull;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;
import ru.fitsme.android.domain.entities.favourites.FavouritesItem;

public interface IFavouritesInteractor {

    @NonNull
    Single<Integer> getLastIndexSingle();

    @NonNull
    Single<FavouritesItem> getSingleFavouritesItem(int index);

    @NonNull
    Single<List<FavouritesItem>> getSingleFavouritesPage(int page);

    @NonNull
    Completable removeItemFromFavourites(int index);

    @NonNull
    Completable restoreItemToFavourites(int index);

    @NonNull
    Completable addFavouritesItemToCart(int index);
}
