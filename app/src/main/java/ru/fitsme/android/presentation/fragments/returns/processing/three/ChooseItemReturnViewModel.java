package ru.fitsme.android.presentation.fragments.returns.processing.three;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import androidx.databinding.ObservableBoolean;
import androidx.lifecycle.MutableLiveData;
import ru.fitsme.android.data.frameworks.retrofit.entities.ReturnsItemRequest;
import ru.fitsme.android.domain.entities.order.Order;
import ru.fitsme.android.domain.entities.order.OrderItem;
import ru.fitsme.android.domain.entities.order.ReturnsOrder;
import ru.fitsme.android.domain.interactors.returns.IReturnsInteractor;
import ru.fitsme.android.presentation.fragments.base.BaseViewModel;
import timber.log.Timber;

public class ChooseItemReturnViewModel extends BaseViewModel {

    private final IReturnsInteractor returnsInteractor;

    public ObservableBoolean isLoading = new ObservableBoolean(true);

    private List<ReturnsItemRequest> requestsList = new ArrayList<>();
    private MutableLiveData<String> errorMsgLiveData = new MutableLiveData<>();

    public ChooseItemReturnViewModel(@NotNull IReturnsInteractor returnsInteractor) {
        this.returnsInteractor = returnsInteractor;
        inject(this);
    }

    void init() {
        isLoading.set(false);
        errorMsgLiveData.postValue("");
    }

    public void goToReturnsIndicateNumber(Order returnsOrder) {
        isLoading.set(true);

        for (OrderItem orderItem : returnsOrder.getOrderItemList()) {
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

    private void onError(Throwable throwable) {
        isLoading.set(false);
        Timber.d(throwable);
        errorMsgLiveData.postValue("Возможно, возврат для данного товара уже существует");
//        Timber.e("Некоторые запросы закончились неудачей");
    }

    MutableLiveData<String> getErrorMsgLiveData() {
        return errorMsgLiveData;
    }

    private void onSuccess(ReturnsOrder returnsOrder) {
        if (!requestsList.isEmpty()) {
            sendOneRequest();
        } else {
            isLoading.set(false);
            errorMsgLiveData.postValue("");
            navigation.goToReturnsIndicateNumber(returnsOrder.getId());
        }
    }

    public void backToReturnsChooseOrder() {
        isLoading.set(false);
        navigation.backToReturnsChooseOrder();
    }
}
