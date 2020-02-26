package ru.fitsme.android.presentation.fragments.returns.processing.one;

import ru.fitsme.android.presentation.fragments.base.BaseViewModel;

public class HowToReturnViewModel extends BaseViewModel {

    public HowToReturnViewModel() {
        inject(this);
    }

    public void goToReturnsChooseOrder() {
        navigation.goToReturnsChooseOrderWithReplace();
    }
}
