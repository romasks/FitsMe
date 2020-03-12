package ru.fitsme.android.presentation.fragments.returns.processing.five;

import androidx.databinding.ObservableBoolean;
import androidx.lifecycle.MutableLiveData;

import javax.inject.Inject;

import ru.fitsme.android.data.frameworks.retrofit.entities.ReturnsPaymentRequest;
import ru.fitsme.android.domain.entities.returns.ReturnsOrder;
import ru.fitsme.android.domain.entities.returns.ReturnsOrderItem;
import ru.fitsme.android.domain.interactors.returns.IReturnsInteractor;
import ru.fitsme.android.presentation.fragments.base.BaseViewModel;
import ru.fitsme.android.utils.OrderStatus;
import timber.log.Timber;

public class BillingInfoReturnViewModel extends BaseViewModel {

    @Inject
    IReturnsInteractor returnsInteractor;

    private int returnId = 0;

    public ObservableBoolean isLoading = new ObservableBoolean(true);
    private MutableLiveData<ReturnsOrder> returnsOrderLiveData = new MutableLiveData<>();

    public BillingInfoReturnViewModel() {
        inject(this);
    }

    void init(int returnId) {
        isLoading.set(false);
        this.returnId = returnId;
        addDisposable(returnsInteractor.getReturnById(returnId)
            .subscribe(this::onGetResult, this::onError));
    }

    public void goToReturnsVerifyData(String cardNumber) {
        String deliveryDetails = returnsOrderLiveData.getValue() == null ? "" :
            returnsOrderLiveData.getValue().getDeliveryDetails();
        addDisposable(returnsInteractor.changeReturnsPayment(
                new ReturnsPaymentRequest(returnId, deliveryDetails, cardNumber, OrderStatus.FM.toString()))
                .subscribe(this::onSuccess, this::onError)
        );
    }

    public MutableLiveData<ReturnsOrder> getReturnsOrderLiveData() {
        return returnsOrderLiveData;
    }

    private void onGetResult(ReturnsOrder returnsOrder) {
        isLoading.set(false);
        returnsOrderLiveData.setValue(returnsOrder);
    }

    private void onError(Throwable throwable) {
        isLoading.set(false);
        Timber.tag(getClass().getName()).e(throwable);
    }

    private void onSuccess(ReturnsOrderItem returnsOrder) {
        navigation.goToReturnsVerifyDataWithReplace(returnsOrder.getId());
    }

    @Override
    public void onBackPressed() {
        navigation.goToReturnsIndicateNumberWithReplace(returnId);
    }
}
