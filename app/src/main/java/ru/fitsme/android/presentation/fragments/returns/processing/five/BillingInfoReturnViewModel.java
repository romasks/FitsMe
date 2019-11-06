package ru.fitsme.android.presentation.fragments.returns.processing.five;

import androidx.databinding.ObservableBoolean;

import org.jetbrains.annotations.NotNull;

import ru.fitsme.android.domain.interactors.returns.IReturnsInteractor;
import ru.fitsme.android.presentation.fragments.base.BaseViewModel;

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

    public void goToReturnsVerifyData() {
//        navigation.goToReturnsVerifyData();
    }

    public void backToReturnsIndicateNumber() {
        navigation.backToReturnsIndicateNumber();
    }
}
