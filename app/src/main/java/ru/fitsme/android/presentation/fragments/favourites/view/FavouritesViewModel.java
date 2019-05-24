package ru.fitsme.android.presentation.fragments.favourites.view;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.paging.PagedList;
import android.support.annotation.NonNull;

import org.jetbrains.annotations.NotNull;

import io.reactivex.disposables.CompositeDisposable;
import ru.fitsme.android.domain.entities.favourites.FavouritesItem;
import ru.fitsme.android.domain.interactors.favourites.IFavouritesInteractor;

//import static ru.fitsme.android.utils.Constants.GONE;

public class FavouritesViewModel extends ViewModel {

    private final String TAG = getClass().getName();

    private final IFavouritesInteractor favouritesInteractor;

    private CompositeDisposable disposable;

    private FavouritesViewModel(@NotNull IFavouritesInteractor favouritesInteractor) {
        this.favouritesInteractor = favouritesInteractor;
    }

    void init() {
        disposable = new CompositeDisposable();
    }

    LiveData<PagedList<FavouritesItem>> getPageLiveData() {
        return favouritesInteractor.getPagedListLiveData();
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.dispose();
    }

//    public void addItemToCart(int position) {
//        disposable.add(
//                favouritesInteractor.addFavouritesItemToCart(position, 0)
//                        .subscribe(() -> {
//                            favouritesInteractor.addFavouritesItemToCart()
//                        }, throwable -> {
//                        })
//        );
//    }

    void deleteItem(Integer position) {
        disposable.add(
                favouritesInteractor
                        .deleteFavouriteItem(position)
                        .subscribe()
        );
    }

    void onInCartBtnClicked(int position, int quantity) {
        disposable.add(
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
}
