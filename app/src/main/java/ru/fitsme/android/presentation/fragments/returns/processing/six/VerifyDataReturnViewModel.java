package ru.fitsme.android.presentation.fragments.returns.processing.six;

import org.jetbrains.annotations.NotNull;

import androidx.databinding.ObservableBoolean;
import ru.fitsme.android.domain.entities.returns.ReturnsItem;
import ru.fitsme.android.domain.interactors.returns.IReturnsInteractor;
import ru.fitsme.android.presentation.fragments.base.BaseViewModel;

public class VerifyDataReturnViewModel extends BaseViewModel {

    private final IReturnsInteractor returnsInteractor;

    public ObservableBoolean isLoading = new ObservableBoolean(true);

    public VerifyDataReturnViewModel(@NotNull IReturnsInteractor returnsInteractor) {
        this.returnsInteractor = returnsInteractor;
        inject(this);
    }

    void init() {
        isLoading.set(false);
    }

    public void sendReturnOrder(ReturnsItem returnsItem) {
        returnsInteractor.sendReturnOrder(returnsItem);
    }

    public void backToReturnsBillingInfo() {
        navigation.backToReturnsBillingInfo();
    }
}
