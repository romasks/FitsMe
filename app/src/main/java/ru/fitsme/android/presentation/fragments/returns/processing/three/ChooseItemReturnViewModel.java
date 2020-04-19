package ru.fitsme.android.presentation.fragments.returns.processing.three;

import androidx.databinding.ObservableBoolean;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import ru.fitsme.android.data.frameworks.retrofit.entities.ReturnsItemRequest;
import ru.fitsme.android.domain.entities.order.Order;
import ru.fitsme.android.domain.entities.order.OrderItem;
import ru.fitsme.android.domain.entities.returns.ReturnsOrderItem;
import ru.fitsme.android.domain.interactors.orders.IOrdersInteractor;
import ru.fitsme.android.domain.interactors.returns.IReturnsInteractor;
import ru.fitsme.android.presentation.fragments.base.BaseViewModel;
import timber.log.Timber;

public class ChooseItemReturnViewModel extends BaseViewModel {

    @Inject
    IOrdersInteractor ordersInteractor;

    @Inject
    IReturnsInteractor returnsInteractor;

    public ObservableBoolean isLoading = new ObservableBoolean(true);

    private List<ReturnsItemRequest> requestsList = new ArrayList<>();
    private MutableLiveData<Order> orderLiveData = new MutableLiveData<>();
    private MutableLiveData<String> errorMsgLiveData = new MutableLiveData<>();

    public ChooseItemReturnViewModel() {
        inject(this);
    }

    LiveData<Order> getOrderLiveData() {
        return orderLiveData;
    }

    LiveData<String> getErrorMsgLiveData() {
        return errorMsgLiveData;
    }

    void init(int orderId) {
        errorMsgLiveData.postValue("");
        isLoading.set(true);
        addDisposable(ordersInteractor.getOrderById(orderId)
                .subscribe(this::onLoadOrder, this::onError));
    }

    @Override
    public void onBackPressed() {
        isLoading.set(false);
        navigation.goToReturnsChooseOrderWithReplace();
    }

    public void goToReturnsIndicateNumber() {
        isLoading.set(true);

        for (OrderItem orderItem : orderLiveData.getValue().getOrderItemList()) {
            if (orderItem.getClothe().isCheckedForReturn()) {
                requestsList.add(new ReturnsItemRequest(orderItem.getId(), 1));
            }
        }
        addDisposable(returnsInteractor.addItemsToReturn(requestsList)
                .subscribe(this::onSuccess, this::onError));
    }

    private void onLoadOrder(Order order) {
        orderLiveData.setValue(order);
    }

    private void onSuccess(ReturnsOrderItem returnsOrder) {
        isLoading.set(false);
        errorMsgLiveData.postValue("");
        navigation.goToReturnsIndicateNumberWithReplace(returnsOrder.getId());
    }

    private void onError(Throwable throwable) {
        isLoading.set(false);
        Timber.d(throwable);
        errorMsgLiveData.postValue("Некоторые запросы закончились неудачей");
    }
}
