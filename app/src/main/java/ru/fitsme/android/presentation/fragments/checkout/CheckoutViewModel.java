package ru.fitsme.android.presentation.fragments.checkout;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;

import org.jetbrains.annotations.NotNull;

import io.reactivex.disposables.CompositeDisposable;
import ru.fitsme.android.data.models.OrderModel;
import ru.fitsme.android.domain.entities.order.Order;
import ru.fitsme.android.domain.interactors.orders.IOrdersInteractor;
import ru.fitsme.android.utils.OrderStatus;
import timber.log.Timber;

import static ru.fitsme.android.utils.Constants.GONE;

public class CheckoutViewModel extends ViewModel {

    private final IOrdersInteractor ordersInteractor;

    private MutableLiveData<Order> orderLiveData;
    private CompositeDisposable disposable;

    public ObservableBoolean loading;
    public ObservableField<OrderModel> orderModel;

    private CheckoutViewModel(@NotNull IOrdersInteractor ordersInteractor) {
        this.ordersInteractor = ordersInteractor;
    }

    void init() {
        orderLiveData = new MutableLiveData<>();
        disposable = new CompositeDisposable();
        loading = new ObservableBoolean(GONE);
        orderModel = new ObservableField<>();
        loadOrder();
    }

    private void loadOrder() {
        disposable.add(
                ordersInteractor.getSingleOrder(1)
                        .subscribe(order -> {
                            orderLiveData.setValue(order);
                        }, throwable -> {
                            Timber.tag(getClass().getName()).d(throwable);
                        })
        );
    }

    LiveData<Order> getOrderLiveData() {
        return orderLiveData;
    }

    void onClickMakeOrder(String phone, String street, String house, String apartment) {
        disposable.add(
                ordersInteractor.makeOrder(phone, street, house, apartment, OrderStatus.FM)
                        .subscribe(() -> {
                        }, throwable -> {
                            Timber.tag(getClass().getName()).d(throwable);
                        })
        );
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.dispose();
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
