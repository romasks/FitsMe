package ru.fitsme.android.domain.interactors.cart;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;
import androidx.databinding.ObservableInt;
import androidx.lifecycle.LiveData;
import androidx.paging.PagedList;

import java.util.List;

import io.reactivex.Single;
import ru.fitsme.android.data.frameworks.retrofit.entities.OrderRequest;
import ru.fitsme.android.domain.entities.order.Order;
import ru.fitsme.android.domain.entities.order.OrderItem;
import ru.fitsme.android.domain.interactors.BaseInteractor;
import ru.fitsme.android.utils.OrderStatus;

public interface ICartInteractor extends BaseInteractor {

    @NonNull
    Single<Order> getSingleOrder(OrderStatus status);

    @NonNull
    Single<Integer> removeItemsFromOrder(List<Integer> orderItemsIds);

    @NonNull
    Single<Integer> removeItemFromOrderSingle(Integer orderItemId);

    void invalidateDataSource();

    @NonNull
    Single<OrderItem> removeItemFromOrder(int position);

    @NonNull
    Single<OrderItem> restoreItemToOrder(int position);

    LiveData<PagedList<OrderItem>> getPagedListLiveData();

    Single<List<Order>> getOrders();

    Single<Order> makeOrder(OrderRequest orderModel);

    LiveData<Boolean> getCartIsEmpty();

    ObservableField<String> getMessage();

    boolean itemIsRemoved(int position);

    ObservableInt getTotalPrice();

    void updateTotalPrice();

    ObservableBoolean getCheckOutIsLoading();

    Single<Order> getOrderById(int orderId);

    void updateList();
}
