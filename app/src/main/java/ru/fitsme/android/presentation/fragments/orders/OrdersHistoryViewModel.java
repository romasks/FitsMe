package ru.fitsme.android.presentation.fragments.orders;

import androidx.databinding.ObservableBoolean;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import javax.inject.Inject;

import ru.fitsme.android.domain.entities.order.Order;
import ru.fitsme.android.domain.interactors.orders.IOrdersInteractor;
import ru.fitsme.android.presentation.fragments.base.BaseViewModel;
import timber.log.Timber;

public class OrdersHistoryViewModel extends BaseViewModel {

    @Inject
    IOrdersInteractor ordersInteractor;

    public ObservableBoolean isLoading = new ObservableBoolean(true);
    public ObservableBoolean isEmpty = new ObservableBoolean(true);
    private MutableLiveData<List<Order>> ordersListLiveData = new MutableLiveData<>();

    public OrdersHistoryViewModel() {
        inject(this);
    }

    @Override
    protected void init() {
        addDisposable(ordersInteractor.getOrders()
                .subscribe(this::onGetResult, this::onError));
    }

    private void onError(Throwable throwable) {
        isLoading.set(false);
        isEmpty.set(true);
        Timber.tag(getClass().getName()).e(throwable);
    }

    private void onGetResult(List<Order> orders) {
        isLoading.set(false);
        isEmpty.set(orders == null || orders.size() == 0);
        ordersListLiveData.setValue(orders);
    }

    MutableLiveData<List<Order>> getOrdersListLiveData() {
        return ordersListLiveData;
    }

    public void goToFavourites() {
        navigation.goToFavourites();
    }

    public void goToOrderDetails(Order order) {
        navigation.goToOrderDetails(order);
    }
}
