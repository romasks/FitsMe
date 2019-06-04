package ru.fitsme.android.presentation.fragments.favourites;

import android.arch.lifecycle.LiveData;
import android.arch.paging.PagedList;

import org.jetbrains.annotations.NotNull;

import ru.fitsme.android.domain.entities.favourites.FavouritesItem;
import ru.fitsme.android.domain.interactors.favourites.IFavouritesInteractor;
import ru.fitsme.android.presentation.fragments.base.BaseViewModel;
import timber.log.Timber;

public class FavouritesViewModel extends BaseViewModel {

    private final IFavouritesInteractor favouritesInteractor;

    public FavouritesViewModel(@NotNull IFavouritesInteractor favouritesInteractor) {
        this.favouritesInteractor = favouritesInteractor;
    }

    LiveData<PagedList<FavouritesItem>> getPageLiveData() {
        return favouritesInteractor.getPagedListLiveData();
    }

    void deleteItem(Integer position) {
        addDisposable(favouritesInteractor
                .deleteFavouriteItem(position)
                .subscribe(() -> {}, this::onError)
        );
    }

    public void addItemToCart(Integer position) {
        addDisposable(favouritesInteractor
                .addFavouritesItemToCart(position)
                .subscribe(() -> {}, this::onError)
        );
    }

    private void onError(Throwable throwable) {
        Timber.tag(getClass().getName()).e(throwable);
    }
}
