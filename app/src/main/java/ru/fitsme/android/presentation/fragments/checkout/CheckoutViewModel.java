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
import ru.fitsme.android.presentation.fragments.base.BaseViewModel;
import ru.fitsme.android.utils.OrderStatus;
import timber.log.Timber;

public class CheckoutViewModel extends BaseViewModel {

    @Inject
    ICartInteractor cartInteractor;

    private MutableLiveData<Boolean> successMakeOrderLiveData;

    public ObservableBoolean isLoading;
    public ObservableField<OrderModel> orderModel;

    public CheckoutViewModel() {
        inject(this);
    }

    LiveData<Boolean> getSuccessMakeOrderLiveData() {
        return successMakeOrderLiveData;
    }

    void onClickMakeOrder() {
        addDisposable(cartInteractor.makeOrder(new OrderRequest(orderModel.get()))
                .subscribe(this::onMakeOrder, Timber::e));
    }

    @Override
    protected void init() {
        successMakeOrderLiveData = new MutableLiveData<>(false);
        isLoading = cartInteractor.getCheckOutIsLoading();
        orderModel = new ObservableField<>();
        loadOrder();
    }

    private void loadOrder() {
        addDisposable(cartInteractor.getSingleOrder(OrderStatus.FM)
                .subscribe(this::onOrder, Timber::e));
    }

    private void onOrder(@NotNull Order order) {
        if (order.getOrderId() != 0) {
            orderModel.set(new OrderModel(order));
        }
    }

    private void onMakeOrder(Order order) {
        if (order.getOrderId() != 0) {
            Timber.tag(getClass().getName()).d("SUCCESS");
            successMakeOrderLiveData.setValue(true);
        }
    }
}
