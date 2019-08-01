package ru.fitsme.android.presentation.fragments.checkout;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;

import org.jetbrains.annotations.NotNull;

import ru.fitsme.android.data.models.OrderModel;
import ru.fitsme.android.domain.entities.order.Order;
import ru.fitsme.android.domain.interactors.orders.IOrdersInteractor;
import ru.fitsme.android.presentation.fragments.base.BaseViewModel;
import ru.fitsme.android.utils.OrderStatus;
import timber.log.Timber;

public class CheckoutViewModel extends BaseViewModel {

    private final IOrdersInteractor ordersInteractor;

    private MutableLiveData<Order> orderLiveData;
    private MutableLiveData<Boolean> successMakeOrderLiveData;

    public ObservableBoolean isLoading;
    public ObservableField<OrderModel> orderModel;

    public CheckoutViewModel(@NotNull IOrdersInteractor ordersInteractor) {
        this.ordersInteractor = ordersInteractor;
    }

    LiveData<Order> getOrderLiveData() {
        return orderLiveData;
    }

    LiveData<Boolean> getSuccessMakeOrderLiveData() {
        return successMakeOrderLiveData;
    }

    void init() {
        orderLiveData = new MutableLiveData<>();
        successMakeOrderLiveData = new MutableLiveData<>();
        successMakeOrderLiveData.setValue(false);
        isLoading = ordersInteractor.getCheckOutIsLoading();
        orderModel = new ObservableField<>();
        loadOrder();
    }

    private void loadOrder() {
        ordersInteractor.getSingleOrder(OrderStatus.FM)
                .subscribe(this::onOrder, Timber::e);
    }

    void onClickMakeOrder() {
        ordersInteractor.makeOrder(orderModel.get())
                .subscribe(this::onMakeOrder, Timber::e);
    }

    private void onOrder(@NotNull Order order) {
        if (order.getOrderId() != 0) {
            orderLiveData.setValue(order);
        }
    }

    private void onMakeOrder(Order order) {
        if (order.getOrderId() != 0) {
            Timber.tag(getClass().getName()).d("SUCCESS");
            successMakeOrderLiveData.setValue(true);
        }
    }
}
