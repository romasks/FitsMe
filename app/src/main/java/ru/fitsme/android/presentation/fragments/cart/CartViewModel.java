package ru.fitsme.android.presentation.fragments.cart;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableInt;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import ru.fitsme.android.domain.entities.order.Order;
import ru.fitsme.android.domain.entities.order.OrderItem;
import ru.fitsme.android.domain.interactors.orders.IOrdersInteractor;
import ru.fitsme.android.presentation.fragments.base.BaseViewModel;
import ru.fitsme.android.utils.OrderStatus;
import timber.log.Timber;

import static ru.fitsme.android.utils.Constants.GONE;

public class CartViewModel extends BaseViewModel {

    private IOrdersInteractor ordersInteractor;
    private CartAdapter adapter;

    private MutableLiveData<List<OrderItem>> pageLiveData;

    public ObservableBoolean loading;
    public ObservableBoolean showEmpty;
    public ObservableInt totalPrice;

    public CartViewModel(@NotNull IOrdersInteractor ordersInteractor) {
        this.ordersInteractor = ordersInteractor;
    }

    void init() {
        pageLiveData = new MutableLiveData<>();
        loading = new ObservableBoolean(GONE);
        showEmpty = new ObservableBoolean(GONE);
        totalPrice = new ObservableInt();
        totalPrice.set(0);
        loadCart();
    }

    void setAdapter(int layoutId) {
        adapter = new CartAdapter(layoutId, this);
    }

    public CartAdapter getAdapter() {
        return adapter;
    }

    public void setOrderItemsInAdapter(List<OrderItem> orderItems) {

        if (adapter.getItemCount() == 0) {
            adapter.setOrderItems(orderItems);
        } else {
            adapter.addOrderItems(orderItems);
        }
        adapter.notifyDataSetChanged();
    }

    public LiveData<List<OrderItem>> getPageLiveData() {
        return pageLiveData;
    }

    private OrderItem getOrderItemAt(int index) {
        return adapter.getOrderItemAt(index);
    }

    private void loadCart() {
        addDisposable(ordersInteractor.getSingleOrder(OrderStatus.FM)
                .subscribe(this::onOrder, this::onError));
    }

    private void onOrder(@NotNull Order order) {
        pageLiveData.setValue(order.getOrderItemList());
        int tmpPrice = 0;
        for (OrderItem oi : order.getOrderItemList()) {
            tmpPrice += oi.getPrice();
        }
        totalPrice.set(tmpPrice);
        Timber.tag(getClass().getName()).d("SUCCESS " +
                order.getOrderId() + " " +
                order.getApartment() + " " +
                order.getPhoneNumber());
        loading.set(GONE);
    }

    private void onError(Throwable throwable) {
        Timber.tag(getClass().getName()).d("FAIL");
    }

    public OrderItem getCartItemAt(int index) {
        return adapter.getOrderItemAt(index);
    }

}
