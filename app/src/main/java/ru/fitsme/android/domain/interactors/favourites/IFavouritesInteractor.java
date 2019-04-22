package ru.fitsme.android.domain.interactors.favourites;

import android.support.annotation.NonNull;


import io.reactivex.Completable;
import io.reactivex.Single;
import ru.fitsme.android.data.entities.response.favourites.FavouritesPage;
import ru.fitsme.android.data.entities.response.favourites.FavouritesItem;

public interface IFavouritesInteractor {

    String TAG = "FavouritesInteractor";

    @NonNull
    Single<Integer> getLastIndexSingle();

    @NonNull
    Single<FavouritesItem> getSingleFavouritesItem(int index);

    @NonNull
    Single<FavouritesPage> getSingleFavouritesPage(int page);

    @NonNull
    Completable restoreItemToFavourites(int index);

    @NonNull
    Completable addFavouritesItemToCart(int index, int quantity);

    @NonNull
    Completable deleteFavouriteItem(Integer index);
}
