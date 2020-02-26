package ru.fitsme.android.presentation.fragments.returns.processing.six;

import androidx.databinding.ObservableBoolean;
import androidx.lifecycle.MutableLiveData;

import javax.inject.Inject;

import ru.fitsme.android.data.frameworks.retrofit.entities.ReturnsPaymentRequest;
import ru.fitsme.android.domain.entities.returns.ReturnsOrder;
import ru.fitsme.android.domain.entities.returns.ReturnsOrderItem;
import ru.fitsme.android.domain.interactors.returns.IReturnsInteractor;
import ru.fitsme.android.presentation.fragments.base.BaseViewModel;
import ru.fitsme.android.utils.OrderStatus;
import ru.fitsme.android.utils.ReturnsOrderStep;
import timber.log.Timber;

public class VerifyDataReturnViewModel extends BaseViewModel {

    @Inject
    IReturnsInteractor returnsInteractor;

    public ObservableBoolean isLoading = new ObservableBoolean(true);
    private MutableLiveData<ReturnsOrder> returnsOrderLiveData = new MutableLiveData<>();

    public VerifyDataReturnViewModel() {
        inject(this);
        returnsInteractor.setReturnOrderStep(ReturnsOrderStep.VERIFY_DATA);
    }

    public MutableLiveData<ReturnsOrder> getReturnsOrderLiveData() {
        return returnsOrderLiveData;
    }

    @Override
    protected void init() {
        isLoading.set(true);
        int returnId = returnsInteractor.getReturnId();
        addDisposable(returnsInteractor.getReturnById(returnId)
                .subscribe(this::onSuccess, this::onError));
    }

    private void onSuccess(ReturnsOrder returnsOrder) {
        isLoading.set(false);
        returnsOrderLiveData.setValue(returnsOrder);
    }

    private void onError(Throwable throwable) {
        isLoading.set(false);
        Timber.d(throwable);
    }

    public void sendReturnOrder() {
        int returnId = returnsInteractor.getReturnId();
        addDisposable(returnsInteractor.changeReturnsPayment(
                new ReturnsPaymentRequest(returnId, null, null, OrderStatus.ISU.toString())
        ).subscribe(this::onSuccessConfirm, this::onError));
    }

    private void onSuccessConfirm(ReturnsOrderItem returnsOrderItem) {
        returnsInteractor.setReturnOrderStep(ReturnsOrderStep.HOW_TO);
        navigation.goToOrdersReturnWithReplace();
    }
}
