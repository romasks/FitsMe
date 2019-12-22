package ru.fitsme.android.presentation.fragments.returns.processing.four;

import androidx.databinding.ObservableBoolean;

import javax.inject.Inject;

import ru.fitsme.android.data.frameworks.retrofit.entities.ReturnsPaymentRequest;
import ru.fitsme.android.domain.entities.returns.ReturnsOrderItem;
import ru.fitsme.android.domain.interactors.returns.IReturnsInteractor;
import ru.fitsme.android.presentation.fragments.base.BaseViewModel;
import ru.fitsme.android.utils.OrderStatus;
import timber.log.Timber;

public class IndicateNumberReturnViewModel extends BaseViewModel {

    @Inject
    IReturnsInteractor returnsInteractor;

    public ObservableBoolean isLoading = new ObservableBoolean(true);

    public IndicateNumberReturnViewModel() {
        inject(this);
        if (returnsInteractor.getReturnOrderStep() < 4)
            returnsInteractor.setReturnOrderStep(4);
    }

    @Override
    protected void init() {
        isLoading.set(false);
    }

    public void goToReturnsBillingInfo(String indicationNumber, int returnId) {
        addDisposable(returnsInteractor.changeReturnsPayment(
                new ReturnsPaymentRequest(
                        returnId, indicationNumber, null, OrderStatus.FM.toString()
                ))
                .subscribe(this::onSuccess, this::onError)
        );
    }

    private void onError(Throwable throwable) {
        Timber.d(throwable);
    }

    private void onSuccess(ReturnsOrderItem returnsOrder) {
        returnsInteractor.setReturnId(returnsOrder.getId());
        navigation.goToReturnsBillingInfo(returnsOrder.getId());
    }
}
