package ru.fitsme.android.presentation.fragments.cart;

import android.arch.lifecycle.LiveData;
import android.arch.paging.PagedList;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableInt;

import org.jetbrains.annotations.NotNull;

import ru.fitsme.android.domain.entities.order.OrderItem;
import ru.fitsme.android.domain.interactors.orders.IOrdersInteractor;
import ru.fitsme.android.presentation.fragments.base.BaseViewModel;

import static ru.fitsme.android.utils.Constants.GONE;

public class CartViewModel extends BaseViewModel {

    private final IOrdersInteractor ordersInteractor;

    public ObservableBoolean loading;
    public ObservableBoolean showEmpty;
    public ObservableInt totalPrice;

    public CartViewModel(@NotNull IOrdersInteractor ordersInteractor) {
        this.ordersInteractor = ordersInteractor;
    }

    public void init() {
        loading = new ObservableBoolean(GONE);
        showEmpty = new ObservableBoolean(GONE);
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
}
