package ru.fitsme.android.presentation.fragments.favourites;

import android.arch.lifecycle.LiveData;
import android.arch.paging.PagedList;
import android.databinding.ObservableField;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Single;
import ru.fitsme.android.domain.entities.favourites.FavouritesItem;
import ru.fitsme.android.domain.entities.order.OrderItem;
import ru.fitsme.android.domain.interactors.favourites.IFavouritesInteractor;
import ru.fitsme.android.domain.interactors.profile.IProfileInteractor;
import ru.fitsme.android.presentation.fragments.base.BaseViewModel;

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

    Single<FavouritesItem> removeItem(Integer position) {
        return favouritesInteractor.removeFavouriteItem(position);
    }

    public Single<OrderItem> addItemToCart(Integer position) {
        return favouritesInteractor.addFavouritesItemToCart(position);
    }

    Single<FavouritesItem> restoreItem(int position){
        return favouritesInteractor.restoreItemToFavourites(position);
    }

    boolean itemIsRemoved(int position) {
        return favouritesInteractor.itemIsRemoved(position);
    }
}
