package ru.fitsme.android.presentation.fragments.returns.processing.one;

import android.databinding.ObservableBoolean;

import org.jetbrains.annotations.NotNull;

import ru.fitsme.android.domain.interactors.returns.IReturnsInteractor;
import ru.fitsme.android.presentation.fragments.base.BaseViewModel;

public class HowToReturnViewModel extends BaseViewModel {

    private final IReturnsInteractor returnsInteractor;

    public ObservableBoolean isLoading = new ObservableBoolean(true);

    public HowToReturnViewModel(@NotNull IReturnsInteractor returnsInteractor) {
        this.returnsInteractor = returnsInteractor;
        inject(this);
    }

    void init() {
        isLoading.set(false);
    }

    public void goToReturnsChooseOrder() {
        navigation.goToReturnsChooseOrder();
    }

    public void backToOrdersReturn() {
        navigation.backToOrdersReturn();
    }
}
