package ru.fitsme.android.domain.interactors.favourites;

import android.arch.lifecycle.LiveData;
import android.arch.paging.PagedList;
import android.support.annotation.NonNull;


import io.reactivex.Completable;
import io.reactivex.Single;
import ru.fitsme.android.data.repositories.favourites.entity.FavouritesPage;
import ru.fitsme.android.domain.entities.favourites.FavouritesItem;

public interface IFavouritesInteractor {

    String TAG = "FavouritesInteractor";

//    @NonNull
//    Single<Integer> getLastIndexSingle();
//
//    @NonNull
//    Single<FavouritesItem> getSingleFavouritesItem(int index);
//
//    @NonNull
//    Single<FavouritesPage> getSingleFavouritesPage(int page);
//
//    @NonNull
//    Completable restoreItemToFavourites(int index);

    @NonNull
    Completable addFavouritesItemToCart(int index, int quantity);

    @NonNull
    Completable deleteFavouriteItem(Integer index);

    LiveData<PagedList<FavouritesItem>> getPagedListLiveData();
}
