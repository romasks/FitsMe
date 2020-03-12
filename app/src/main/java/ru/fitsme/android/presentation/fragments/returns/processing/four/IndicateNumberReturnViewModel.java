package ru.fitsme.android.presentation.fragments.returns.processing.four;

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

public class IndicateNumberReturnViewModel extends BaseViewModel {

    @Inject
    IReturnsInteractor returnsInteractor;

    public ObservableBoolean isLoading = new ObservableBoolean(true);
    private MutableLiveData<ReturnsOrder> returnsOrderLiveData = new MutableLiveData<>();

    public IndicateNumberReturnViewModel() {
        inject(this);
    }

    void init(int returnId) {
        isLoading.set(false);
        addDisposable(returnsInteractor.getReturnById(returnId)
            .subscribe(this::onGetResult, this::onError));
    }

    public void goToReturnsBillingInfo(int returnId, String indicationNumber) {
        String paymentDetails = returnsOrderLiveData.getValue() == null ? "" :
            returnsOrderLiveData.getValue().getPaymentDetails();

        addDisposable(returnsInteractor.changeReturnsPayment(
            new ReturnsPaymentRequest(returnId, indicationNumber, paymentDetails, OrderStatus.FM.toString()))
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
        navigation.goToReturnsBillingInfoWithReplace(returnsOrder.getId());
    }

    @Override
    public void onBackPressed() {
        navigation.goToReturnsChooseOrderWithReplace();
    }
}
