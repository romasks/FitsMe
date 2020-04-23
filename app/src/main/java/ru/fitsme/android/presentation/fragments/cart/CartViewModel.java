package ru.fitsme.android.presentation.fragments.cart;

import androidx.databinding.ObservableField;
import androidx.databinding.ObservableInt;
import androidx.lifecycle.LiveData;
import androidx.paging.PagedList;

import javax.inject.Inject;

import io.reactivex.Single;
import ru.fitsme.android.domain.entities.order.OrderItem;
import ru.fitsme.android.domain.interactors.cart.ICartInteractor;
import ru.fitsme.android.presentation.fragments.base.BaseViewModel;
import ru.fitsme.android.presentation.fragments.iteminfo.ClotheInfo;

public class CartViewModel extends BaseViewModel{

    @Inject
    ICartInteractor cartInteractor;

    public ObservableField<String> message;
    public ObservableInt totalPrice;

    public CartViewModel() {
        inject(this);
    }

    public void init() {
        message = cartInteractor.getMessage();
        totalPrice = cartInteractor.getTotalPrice();
    }

    LiveData<PagedList<OrderItem>> getPageLiveData() {
        return cartInteractor.getPagedListLiveData();
    }

    LiveData<Boolean> getCartIsEmpty() {
        return cartInteractor.getCartIsEmpty();
    }

    public Single<OrderItem> removeItemFromOrder(int position) {
        return cartInteractor.removeItemFromOrder(position);
    }

    Single<OrderItem> restoreItemToOrder(int position) {
        return cartInteractor.restoreItemToOrder(position);
    }

    boolean itemIsRemoved(int position) {
        return cartInteractor.itemIsRemoved(position);
    }

    @Override
    public void onBackPressed() {
        navigation.finish();
    }

    public void setDetailView(ClotheInfo clotheInfo) {
        navigation.goToDetailItemInfo(clotheInfo);
    }

    public void goToCheckout() {
        navigation.goToCheckout();
    }

    public void goToFavourites() {
        navigation.goToFavourites();
    }

    public void goToRateItems() {
        navigation.goToRateItems();
    }

    public void updateList() {
        cartInteractor.updateList();
    }
}
