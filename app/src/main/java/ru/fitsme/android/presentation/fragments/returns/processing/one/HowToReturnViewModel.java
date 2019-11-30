package ru.fitsme.android.presentation.fragments.returns.processing.one;

import androidx.databinding.ObservableBoolean;

import ru.fitsme.android.presentation.fragments.base.BaseViewModel;

public class HowToReturnViewModel extends BaseViewModel {

    public ObservableBoolean isLoading = new ObservableBoolean(true);

    public HowToReturnViewModel() {
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
