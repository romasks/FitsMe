package ru.fitsme.android.presentation.fragments.checkout;

import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;

import ru.fitsme.android.data.frameworks.retrofit.entities.OrderRequest;
import ru.fitsme.android.data.models.OrderModel;
import ru.fitsme.android.domain.entities.order.Order;
import ru.fitsme.android.domain.interactors.cart.ICartInteractor;
import ru.fitsme.android.domain.interactors.clothes.IClothesInteractor;
import ru.fitsme.android.presentation.fragments.base.BaseViewModel;
import ru.fitsme.android.utils.OrderStatus;
import timber.log.Timber;

public class CheckoutViewModel extends BaseViewModel {

    @Inject
    ICartInteractor cartInteractor;

    @Inject
    IClothesInteractor clothesInteractor;

    private MutableLiveData<Order> orderLiveData;
    private MutableLiveData<Boolean> successMakeOrderLiveData;
    private MutableLiveData<Boolean> errorMakeOrderLiveData;
    private int retryMakeOrderCount = 2;

    public ObservableBoolean isLoading;
    public ObservableField<OrderModel> orderModel;

    public CheckoutViewModel() {
        inject(this);
    }

    LiveData<Order> getOrderLiveData() {
        return orderLiveData;
    }

    LiveData<Boolean> getSuccessMakeOrderLiveData() {
        return successMakeOrderLiveData;
    }

    LiveData<Boolean> getErrorMakeOrderLiveData() {
        return errorMakeOrderLiveData;
    }

    public void setPhoneNumber(@NotNull String phoneNumber) {
        orderModel.get().setPhoneNumber(phoneNumber);
    }

    public void onClickMakeOrder() {
        addDisposable(cartInteractor.makeOrder(new OrderRequest(orderModel.get()))
                .subscribe(this::onSuccessMakeOrder, this::onErrorMakeOrder));
    }

    @Override
    protected void init() {
        orderLiveData = new MutableLiveData<>(null);
        successMakeOrderLiveData = new MutableLiveData<>(false);
        errorMakeOrderLiveData = new MutableLiveData<>(false);
        isLoading = cartInteractor.getCheckOutIsLoading();
        orderModel = new ObservableField<>();
        loadOrder();
    }

    private void loadOrder() {
        addDisposable(cartInteractor.getSingleOrder(OrderStatus.FM)
                .subscribe(this::onOrder, Timber::e));
    }

    private void onOrder(@NotNull Order order) {
        orderLiveData.setValue(order);
        if (order.getOrderId() != 0) {
            orderModel.set(new OrderModel(order));
        }
    }

    private void onSuccessMakeOrder(Order order) {
        Timber.tag(getClass().getName()).d("SUCCESS");
        successMakeOrderLiveData.setValue(true);
    }

    private void onErrorMakeOrder(Throwable throwable) {
        Timber.tag(getClass().getName()).e(throwable);
        if (retryMakeOrderCount-- > 0) onClickMakeOrder();
        else errorMakeOrderLiveData.setValue(true);
    }
}
