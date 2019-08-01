package ru.fitsme.android.domain.interactors.orders;

import android.arch.lifecycle.LiveData;
import android.arch.paging.PagedList;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.support.annotation.NonNull;


import io.reactivex.Single;
import ru.fitsme.android.data.models.OrderModel;
import ru.fitsme.android.domain.entities.order.Order;
import ru.fitsme.android.domain.entities.order.OrderItem;
import ru.fitsme.android.domain.interactors.BaseInteractor;
import ru.fitsme.android.utils.OrderStatus;

public interface IOrdersInteractor extends BaseInteractor{

    @NonNull
    Single<Order> getSingleOrder(OrderStatus status);

    @NonNull
    Single<OrderItem> removeItemFromOrder(int position);

    @NonNull
    Single<OrderItem> restoreItemToOrder(int position);

    LiveData<PagedList<OrderItem>> getPagedListLiveData();

    Single<Order> makeOrder(OrderModel orderModel);

    ObservableBoolean getCartIsEmpty();

    ObservableField<String> getMessage();

    boolean itemIsRemoved(int position);

    ObservableInt getTotalPrice();

    void updateTotalPrice();

    ObservableBoolean getCheckOutIsLoading();
}
