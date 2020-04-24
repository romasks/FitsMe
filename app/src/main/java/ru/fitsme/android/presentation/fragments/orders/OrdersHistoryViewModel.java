package ru.fitsme.android.presentation.fragments.orders;

import androidx.databinding.ObservableField;
import androidx.lifecycle.LiveData;
import androidx.paging.PagedList;

import javax.inject.Inject;

import ru.fitsme.android.domain.entities.order.Order;
import ru.fitsme.android.domain.interactors.orders.IOrdersInteractor;
import ru.fitsme.android.presentation.fragments.base.BaseViewModel;

public class OrdersHistoryViewModel extends BaseViewModel {

    @Inject
    IOrdersInteractor ordersInteractor;

    public ObservableField<String> message;

    public OrdersHistoryViewModel() {
        inject(this);
    }

    @Override
    protected void init() {
        message = ordersInteractor.getMessage();
    }

    LiveData<PagedList<Order>> getOrdersListLiveData() {
        return ordersInteractor.getPagedListLiveData();
    }

    LiveData<Boolean> getOrdersListIsEmpty() {
        return ordersInteractor.getOrdersListIsEmpty();
    }

    public void goToOrderDetails(Order order) {
        navigation.goToOrderDetails(order);
    }
}
