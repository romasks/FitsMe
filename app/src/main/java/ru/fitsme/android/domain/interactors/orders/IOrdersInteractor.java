package ru.fitsme.android.domain.interactors.orders;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;
import androidx.databinding.ObservableInt;
import androidx.lifecycle.LiveData;
import androidx.paging.PagedList;

import java.util.List;

import io.reactivex.Single;
import ru.fitsme.android.data.models.OrderModel;
import ru.fitsme.android.domain.entities.order.Order;
import ru.fitsme.android.domain.entities.order.OrderItem;
import ru.fitsme.android.domain.interactors.BaseInteractor;
import ru.fitsme.android.utils.OrderStatus;

public interface IOrdersInteractor extends BaseInteractor {

    @NonNull
    Single<Order> getSingleOrder(OrderStatus status);

    @NonNull
    Single<OrderItem> removeItemFromOrder(int position);

    @NonNull
    Single<OrderItem> restoreItemToOrder(int position);

    LiveData<PagedList<OrderItem>> getPagedListLiveData();

    Single<List<Order>> getOrders();

    Single<List<Order>> getReturnOrders();

    Single<Order> makeOrder(OrderModel orderModel);

    LiveData<Boolean> getCartIsEmpty();

    ObservableField<String> getMessage();

    boolean itemIsRemoved(int position);

    ObservableInt getTotalPrice();

    void updateTotalPrice();

    ObservableBoolean getCheckOutIsLoading();

    Single<Order> getOrderById(int orderId);
}
