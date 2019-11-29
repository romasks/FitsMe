package ru.fitsme.android.presentation.fragments.returns.processing.four;

import androidx.databinding.ObservableBoolean;

import org.jetbrains.annotations.NotNull;

import ru.fitsme.android.domain.entities.returns.ReturnsItem;
import ru.fitsme.android.domain.interactors.returns.IReturnsInteractor;
import ru.fitsme.android.presentation.fragments.base.BaseViewModel;

public class IndicateNumberReturnViewModel extends BaseViewModel {

    private final IReturnsInteractor returnsInteractor;

    public ObservableBoolean isLoading = new ObservableBoolean(true);

    public IndicateNumberReturnViewModel(@NotNull IReturnsInteractor returnsInteractor) {
        this.returnsInteractor = returnsInteractor;
        inject(this);
    }

    void init() {
        isLoading.set(false);
    }

    public void goToReturnsBillingInfo(ReturnsItem returnsItem) {
        navigation.goToReturnsBillingInfo(returnsItem);
    }

    public void backToReturnsChooseItems() {
        navigation.backToReturnsChooseItems();
    }

    @Override
    public void onBackPressed() {
        navigation.goBack();
    }
}
