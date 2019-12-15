package ru.fitsme.android.presentation.fragments.returns.processing.six;

import javax.inject.Inject;

import androidx.databinding.ObservableBoolean;
import androidx.lifecycle.MutableLiveData;
import ru.fitsme.android.data.frameworks.retrofit.entities.ReturnsPaymentRequest;
import ru.fitsme.android.domain.entities.returns.ReturnsOrder;
import ru.fitsme.android.domain.interactors.returns.IReturnsInteractor;
import ru.fitsme.android.presentation.fragments.base.BaseViewModel;
import ru.fitsme.android.utils.OrderStatus;
import timber.log.Timber;

public class VerifyDataReturnViewModel extends BaseViewModel {

    @Inject
    IReturnsInteractor returnsInteractor;

    public ObservableBoolean isLoading = new ObservableBoolean(true);
    private MutableLiveData<ReturnsOrder> returnsOrderLiveData = new MutableLiveData<>();

    public VerifyDataReturnViewModel() {
        inject(this);
        returnsInteractor.setReturnOrderStep(6);
    }

    public MutableLiveData<ReturnsOrder> getReturnsOrderLiveData() {
        return returnsOrderLiveData;
    }

    void init(int returnId) {
        isLoading.set(true);
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

    public void sendReturnOrder(int returnId) {
        returnsInteractor.changeReturnsPayment(new ReturnsPaymentRequest(returnId, null, null, OrderStatus.ISU.toString()));
        navigation.goToOrdersReturn();
    }

    public void backToReturnsBillingInfo() {
        navigation.backToReturnsBillingInfo();
    }

    @Override
    public void onBackPressed() {
        navigation.goBack();
    }
}
