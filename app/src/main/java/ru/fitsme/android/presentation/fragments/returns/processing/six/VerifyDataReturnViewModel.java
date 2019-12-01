package ru.fitsme.android.presentation.fragments.returns.processing.six;

import org.jetbrains.annotations.NotNull;

import androidx.databinding.ObservableBoolean;
import ru.fitsme.android.domain.entities.order.ReturnsOrder;
import ru.fitsme.android.domain.entities.returns.ReturnsItem;
import ru.fitsme.android.domain.interactors.returns.IReturnsInteractor;
import ru.fitsme.android.presentation.fragments.base.BaseViewModel;
import timber.log.Timber;

public class VerifyDataReturnViewModel extends BaseViewModel {

    private final IReturnsInteractor returnsInteractor;

    public ObservableBoolean isLoading = new ObservableBoolean(true);

    public VerifyDataReturnViewModel(@NotNull IReturnsInteractor returnsInteractor) {
        this.returnsInteractor = returnsInteractor;
        inject(this);
    }

    void init(int returnId) {
        isLoading.set(false);
        addDisposable(returnsInteractor.getReturnById(returnId)
        .subscribe(this::onSuccess, this::onError));
    }

    private void onError(Throwable throwable) {
        Timber.d(throwable);
    }

    private void onSuccess(ReturnsOrder returnsOrder) {

    }

    public void sendReturnOrder(ReturnsItem returnsItem) {
//        returnsInteractor.sendReturnOrder(returnsItem);
//        navigation.goToOrdersReturn();
    }

    public void backToReturnsBillingInfo() {
        navigation.backToReturnsBillingInfo();
    }
}
