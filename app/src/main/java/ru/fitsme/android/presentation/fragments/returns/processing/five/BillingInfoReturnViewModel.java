package ru.fitsme.android.presentation.fragments.returns.processing.five;

import org.jetbrains.annotations.NotNull;

import androidx.databinding.ObservableBoolean;
import ru.fitsme.android.domain.entities.returns.ReturnsItem;
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

    public void goToReturnsVerifyData(ReturnsItem returnsItem) {
        navigation.goToReturnsVerifyData(returnsItem);
    }

    public void backToReturnsChooseItems() {
        navigation.backToReturnsChooseItems();
    }
}
