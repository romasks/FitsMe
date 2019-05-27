package ru.fitsme.android.presentation.fragments.favourites;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.paging.PagedList;
import android.support.annotation.NonNull;

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
        addDisposable(
                favouritesInteractor
                        .deleteFavouriteItem(position)
                        .subscribe()
        );
    }

    void onInCartBtnClicked(int position, int quantity) {
        addDisposable(
                favouritesInteractor.addFavouritesItemToCart(position, quantity)
                        .subscribe()
        );
    }

    static public class Factory implements ViewModelProvider.Factory {
        private final IFavouritesInteractor favouritesInteractor;

        public Factory(@NotNull IFavouritesInteractor favouritesInteractor) {
            this.favouritesInteractor = favouritesInteractor;
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            return (T) new FavouritesViewModel(favouritesInteractor);
        }
    }

    private void onError(Throwable throwable) {
        Timber.tag(getClass().getName()).e(throwable);
    }
}
