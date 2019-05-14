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

import static ru.fitsme.android.utils.Constants.GONE;

public class CheckoutViewModel extends BaseViewModel {

    private final IOrdersInteractor ordersInteractor;

    private MutableLiveData<Order> orderLiveData;

    public ObservableBoolean loading;
    public ObservableField<OrderModel> orderModel;

    public CheckoutViewModel(@NotNull IOrdersInteractor ordersInteractor) {
        this.ordersInteractor = ordersInteractor;
    }

    void init() {
        orderLiveData = new MutableLiveData<>();
        loading = new ObservableBoolean(GONE);
        orderModel = new ObservableField<>();
        loadOrder();
    }

    private void loadOrder() {
        addDisposable(ordersInteractor.getSingleOrder(1)
                .subscribe(this::onOrder, this::onError));
    }

    LiveData<Order> getOrderLiveData() {
        return orderLiveData;
    }

    void onClickMakeOrder(String phone, String street, String house, String apartment) {
        addDisposable(ordersInteractor.makeOrder(phone, street, house, apartment, OrderStatus.FM)
                .subscribe(this::onMakeOrder, this::onError));
    }

    private void onOrder(@NotNull Order order) {
        orderLiveData.setValue(order);
    }

    private void onMakeOrder() {
        Timber.tag(getClass().getName()).d("SUCCESS");
    }

    private void onError(Throwable throwable) {
        Timber.tag(getClass().getName()).e(throwable);
    }
}
