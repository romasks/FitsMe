package ru.fitsme.android.presentation.fragments.favourites;

import javax.inject.Inject;

import androidx.databinding.ObservableField;
import androidx.lifecycle.LiveData;
import androidx.paging.PagedList;
import io.reactivex.Single;
import ru.fitsme.android.domain.entities.favourites.FavouritesItem;
import ru.fitsme.android.domain.entities.order.OrderItem;
import ru.fitsme.android.domain.interactors.favourites.IFavouritesInteractor;
import ru.fitsme.android.presentation.fragments.base.BaseViewModel;

public class FavouritesViewModel extends BaseViewModel {

    @Inject
    IFavouritesInteractor favouritesInteractor;

    public ObservableField<String> showMessage;

    public FavouritesViewModel() {
        inject(this);
    }

    @Override
    protected void init() {
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

    Single<FavouritesItem> restoreItem(int position) {
        return favouritesInteractor.restoreItemToFavourites(position);
    }

    boolean itemIsRemoved(int position) {
        return favouritesInteractor.itemIsRemoved(position);
    }

    public LiveData<Boolean> getFavouritesIsEmpty() {
        return favouritesInteractor.getFavouritesIsEmpty();
    }

    @Override
    public void onBackPressed() {
        navigation.finish();
    }

    public void setDetailView(FavouritesItem favouritesItem) {
        navigation.goToDetailItemInfo(favouritesItem.getItem());
    }

    public void goToRateItems() {
        navigation.goToRateItems();
    }

    void updateList() {
        favouritesInteractor.updateList();
    }
}
