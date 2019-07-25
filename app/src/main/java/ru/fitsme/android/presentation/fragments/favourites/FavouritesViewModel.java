package ru.fitsme.android.presentation.fragments.favourites;

import android.arch.lifecycle.LiveData;
import android.arch.paging.PagedList;
import android.databinding.ObservableField;

import org.jetbrains.annotations.NotNull;

import ru.fitsme.android.domain.entities.favourites.FavouritesItem;
import ru.fitsme.android.domain.interactors.favourites.IFavouritesInteractor;
import ru.fitsme.android.presentation.fragments.base.BaseViewModel;
import timber.log.Timber;

public class FavouritesViewModel extends BaseViewModel {

    private final IFavouritesInteractor favouritesInteractor;

    public ObservableField<String> showMessage;

    public FavouritesViewModel(@NotNull IFavouritesInteractor favouritesInteractor) {
        this.favouritesInteractor = favouritesInteractor;
    }

    void init() {
        showMessage = favouritesInteractor.getShowMessage();
    }

    LiveData<PagedList<FavouritesItem>> getPageLiveData() {
        return favouritesInteractor.getPagedListLiveData();
    }

    void deleteItem(Integer position) {
        favouritesInteractor.deleteFavouriteItem(position);
    }

    public void addItemToCart(Integer position) {
        favouritesInteractor.addFavouritesItemToCart(position);
    }

    public void restoreItem(int position){

    }

    private void onError(Throwable throwable) {
        Timber.tag(getClass().getName()).e(throwable);
    }
}
