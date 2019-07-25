package ru.fitsme.android.domain.interactors.favourites;

import android.arch.lifecycle.LiveData;
import android.arch.paging.PagedList;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;

import ru.fitsme.android.domain.entities.favourites.FavouritesItem;
import ru.fitsme.android.domain.interactors.BaseInteractor;

public interface IFavouritesInteractor extends BaseInteractor {

//    @NonNull
//    Completable restoreItemToFavourites(int index);

    @NonNull
    void addFavouritesItemToCart(int position);

    @NonNull
    void deleteFavouriteItem(Integer position);

    LiveData<PagedList<FavouritesItem>> getPagedListLiveData();

    ObservableField<String> getShowMessage();
}
