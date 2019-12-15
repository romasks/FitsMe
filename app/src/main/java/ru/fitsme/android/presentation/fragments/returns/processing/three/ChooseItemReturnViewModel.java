package ru.fitsme.android.presentation.fragments.returns.processing.three;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import androidx.databinding.ObservableBoolean;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
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
        returnsInteractor.setReturnOrderStep(3);
    }

    void init(long orderId) {
        errorMsgLiveData.postValue("");
        isLoading.set(true);
        addDisposable(ordersInteractor.getOrderById((int) orderId)
                .subscribe(this::onLoadOrder, this::onError));
    }

    public void goToReturnsIndicateNumber() {
        isLoading.set(true);

        for (OrderItem orderItem : orderLiveData.getValue().getOrderItemList()) {
            if (orderItem.getClothe().isCheckedForReturn()) {
                requestsList.add(new ReturnsItemRequest(orderItem.getId(), orderItem.getQuantity()));
            }
        }
        if (!requestsList.isEmpty()) {
            sendOneRequest();
        }
    }

    private void sendOneRequest() {
        ReturnsItemRequest request = requestsList.get(0);
        requestsList.remove(0);

        addDisposable(returnsInteractor.addItemToReturn(request)
                .subscribe(this::onSuccess, this::onError));
    }

    MutableLiveData<String> getErrorMsgLiveData() {
        return errorMsgLiveData;
    }

    public LiveData<Order> getOrderLiveData() {
        return orderLiveData;
    }

    private void onLoadOrder(Order order) {
        orderLiveData.setValue(order);
    }

    private void onSuccess(ReturnsOrderItem returnsOrder) {
        if (!requestsList.isEmpty()) {
            sendOneRequest();
        } else {
            isLoading.set(false);
            errorMsgLiveData.postValue("");
            returnsInteractor.setReturnId(returnsOrder.getId());
            navigation.goToReturnsIndicateNumber(returnsOrder.getId());
        }
    }

    private void onError(Throwable throwable) {
        isLoading.set(false);
        Timber.d(throwable);
        errorMsgLiveData.postValue("Возможно, возврат для данного товара уже существует");
//        Timber.e("Некоторые запросы закончились неудачей");
    }

    public void backToReturnsChooseOrder() {
        isLoading.set(false);
        navigation.backToReturnsChooseOrder();
    }

    @Override
    public void onBackPressed() {
        navigation.goBack();
    }
}
