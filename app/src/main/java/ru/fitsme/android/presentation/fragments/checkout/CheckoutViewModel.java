package ru.fitsme.android.presentation.fragments.checkout;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.databinding.ObservableBoolean;
import android.support.annotation.NonNull;

import org.jetbrains.annotations.NotNull;

import io.reactivex.disposables.Disposable;
import ru.fitsme.android.domain.entities.order.Order;
import ru.fitsme.android.domain.interactors.orders.IOrdersInteractor;
import ru.fitsme.android.utils.OrderStatus;

import static ru.fitsme.android.utils.Constants.GONE;

public class CheckoutViewModel extends ViewModel {

    private final String TAG = getClass().getName();
    private final IOrdersInteractor ordersInteractor;

    private MutableLiveData<Order> orderLiveData;
    private Disposable disposable;

    public ObservableBoolean loading;

    private CheckoutViewModel(@NotNull IOrdersInteractor ordersInteractor) {
        this.ordersInteractor = ordersInteractor;
    }

    void init() {
        orderLiveData = new MutableLiveData<>();
        loading = new ObservableBoolean(GONE);
        loadOrder();
    }

    private void loadOrder() {
        disposable = ordersInteractor.getSingleOrder(1)
                .subscribe(order -> {
                    orderLiveData.setValue(order);
                }, throwable -> {
                });
    }

    LiveData<Order> getOrderLiveData() {
        return orderLiveData;
    }

    public void makeOrder(String phone, String address) {
        disposable = ordersInteractor.makeOrder(phone, address, OrderStatus.FM)
                .subscribe(() -> {
                }, throwable -> {
                });
    }

    static public class Factory implements ViewModelProvider.Factory {
        private final IOrdersInteractor ordersInteractor;

        public Factory(@NotNull IOrdersInteractor ordersInteractor) {
            this.ordersInteractor = ordersInteractor;
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            return (T) new CheckoutViewModel(ordersInteractor);
        }
    }
}
