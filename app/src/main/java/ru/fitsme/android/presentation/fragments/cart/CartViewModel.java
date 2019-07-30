package ru.fitsme.android.presentation.fragments.cart;

import android.arch.lifecycle.LiveData;
import android.arch.paging.PagedList;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;

import org.jetbrains.annotations.NotNull;

import ru.fitsme.android.domain.entities.order.OrderItem;
import ru.fitsme.android.domain.interactors.orders.IOrdersInteractor;
import ru.fitsme.android.presentation.fragments.base.BaseViewModel;
import timber.log.Timber;

public class CartViewModel extends BaseViewModel {

    private final IOrdersInteractor ordersInteractor;

    public ObservableField<String> message;
    public ObservableInt totalPrice;
    public ObservableBoolean cartIsEmpty;

    public CartViewModel(@NotNull IOrdersInteractor ordersInteractor) {
        this.ordersInteractor = ordersInteractor;
    }

    public void init() {
        message = ordersInteractor.getMessage();
        cartIsEmpty = ordersInteractor.getCartIsEmpty();
        totalPrice = new ObservableInt();
        totalPrice.set(0);
    }

    LiveData<PagedList<OrderItem>> getPageLiveData() {
        return ordersInteractor.getPagedListLiveData();
    }

    public void setTotalPrice(PagedList<OrderItem> pagedList) {
        int tmpPrice = 0;
        for (OrderItem oi : pagedList) {
            tmpPrice += oi.getPrice();
        }
        totalPrice.set(tmpPrice);
    }

    public void deleteItem(int position) {
        addDisposable(ordersInteractor
                .removeItemFromOrder(position)
                .subscribe(() -> {}, this::onError)
        );
    }

    private void onError(Throwable throwable) {
        Timber.tag(getClass().getName()).e(throwable);
    }
}
