package ru.fitsme.android.domain.interactors.favourites;

import android.support.annotation.NonNull;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;
import ru.fitsme.android.domain.entities.clothes.ClothesItem;

public interface IFavouritesInteractor {

    @NonNull
    Single<Integer> getLastIndexSingle();

    @NonNull
    Single<ClothesItem> getSingleFavouritesItem(int index);

    @NonNull
    Single<List<ClothesItem>> getSingleFavouritesPage(int firstIndex, int count);

    @NonNull
    Completable removeItemFromFavourites(int index);

    @NonNull
    Completable restoreItemToFavourites(int index);

    @NonNull
    Completable moveFavouritesItemToBasket(int index);
}
