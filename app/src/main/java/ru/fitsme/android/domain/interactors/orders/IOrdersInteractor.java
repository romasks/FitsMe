package ru.fitsme.android.domain.interactors.orders;

import androidx.databinding.ObservableField;
import androidx.lifecycle.LiveData;
import androidx.paging.PagedList;

import java.util.List;

import io.reactivex.Single;
import ru.fitsme.android.domain.entities.order.Order;
import ru.fitsme.android.domain.interactors.BaseInteractor;

public interface IOrdersInteractor extends BaseInteractor {

    LiveData<PagedList<Order>> getPagedListLiveData();

    Single<List<Order>> getOrders();

    Single<List<Order>> getReturnOrders();

    LiveData<Boolean> getOrdersListIsEmpty();

    ObservableField<String> getMessage();

    Single<Order> getOrderById(int orderId);

    void updateList();
}
