package ru.fitsme.android.presentation.fragments.cart;

import javax.inject.Inject;

import androidx.databinding.ObservableField;
import androidx.databinding.ObservableInt;
import androidx.lifecycle.LiveData;
import androidx.paging.PagedList;
import io.reactivex.Single;
import ru.fitsme.android.domain.entities.order.OrderItem;
import ru.fitsme.android.domain.interactors.orders.IOrdersInteractor;
import ru.fitsme.android.presentation.fragments.base.BaseViewModel;

public class CartViewModel extends BaseViewModel {

    @Inject
    IOrdersInteractor ordersInteractor;

    public ObservableField<String> message;
    public ObservableInt totalPrice;

    public CartViewModel() {
        inject(this);
    }

    public void init() {
        message = ordersInteractor.getMessage();
        totalPrice = ordersInteractor.getTotalPrice();
    }

    LiveData<PagedList<OrderItem>> getPageLiveData() {
        return ordersInteractor.getPagedListLiveData();
    }

    LiveData<Boolean> getCartIsEmpty() {
        return ordersInteractor.getCartIsEmpty();
    }

    public Single<OrderItem> removeItemFromOrder(int position) {
        return ordersInteractor.removeItemFromOrder(position);
    }

    Single<OrderItem> restoreItemToOrder(int position) {
        return ordersInteractor.restoreItemToOrder(position);
    }

    boolean itemIsRemoved(int position) {
        return ordersInteractor.itemIsRemoved(position);
    }

    @Override
    public void onBackPressed() {
        navigation.finish();
    }

    public void setDetailView(OrderItem orderItem) {
        navigation.goToDetailItemInfo(orderItem.getClothe());
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
}
