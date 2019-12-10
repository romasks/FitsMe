package ru.fitsme.android.presentation.fragments.returns.processing.five;

import org.jetbrains.annotations.NotNull;

import androidx.databinding.ObservableBoolean;
import ru.fitsme.android.data.frameworks.retrofit.entities.ReturnsPaymentRequest;
import ru.fitsme.android.domain.entities.returns.ReturnsOrderItem;
import ru.fitsme.android.domain.interactors.returns.IReturnsInteractor;
import ru.fitsme.android.presentation.fragments.base.BaseViewModel;
import ru.fitsme.android.utils.OrderStatus;
import timber.log.Timber;

public class BillingInfoReturnViewModel extends BaseViewModel {

    private final IReturnsInteractor returnsInteractor;

    public ObservableBoolean isLoading = new ObservableBoolean(true);

    public BillingInfoReturnViewModel(@NotNull IReturnsInteractor returnsInteractor) {
        this.returnsInteractor = returnsInteractor;
        inject(this);
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
        navigation.goToReturnsVerifyData(returnsOrder.getId());
    }

    public void backToReturnsChooseItems() {
        navigation.backToReturnsChooseItems();
    }

    @Override
    public void onBackPressed() {

    }
}
