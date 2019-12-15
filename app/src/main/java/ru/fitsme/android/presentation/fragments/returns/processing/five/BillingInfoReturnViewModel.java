package ru.fitsme.android.presentation.fragments.returns.processing.five;

import javax.inject.Inject;

import androidx.databinding.ObservableBoolean;
import ru.fitsme.android.data.frameworks.retrofit.entities.ReturnsPaymentRequest;
import ru.fitsme.android.domain.entities.returns.ReturnsOrderItem;
import ru.fitsme.android.domain.interactors.returns.IReturnsInteractor;
import ru.fitsme.android.presentation.fragments.base.BaseViewModel;
import ru.fitsme.android.utils.OrderStatus;
import timber.log.Timber;

public class BillingInfoReturnViewModel extends BaseViewModel {

    @Inject
    IReturnsInteractor returnsInteractor;

    public ObservableBoolean isLoading = new ObservableBoolean(true);

    public BillingInfoReturnViewModel() {
        inject(this);
        if (returnsInteractor.getReturnOrderStep() < 5)
            returnsInteractor.setReturnOrderStep(5);
    }

    void init() {
        isLoading.set(false);
    }

    public void goToReturnsVerifyData(String cardNumber, int returnId) {
        addDisposable(returnsInteractor.changeReturnsPayment(
                new ReturnsPaymentRequest(returnId, null, cardNumber, OrderStatus.FM.toString()))
                .subscribe(this::onSuccess, this::onError)
        );
    }

    private void onError(Throwable throwable) {
        Timber.d(throwable);
    }

    private void onSuccess(ReturnsOrderItem returnsOrder) {
        returnsInteractor.setReturnId(returnsOrder.getId());
        navigation.goToReturnsVerifyData(returnsOrder.getId());
    }

    public void backToReturnsChooseItems() {
        navigation.backToReturnsChooseItems();
    }

    @Override
    public void onBackPressed() {

    }
}
