package ru.fitsme.android.presentation.fragments.returns;

import androidx.databinding.ObservableField;
import androidx.lifecycle.LiveData;
import androidx.paging.PagedList;

import javax.inject.Inject;

import ru.fitsme.android.domain.entities.returns.ReturnsOrder;
import ru.fitsme.android.domain.interactors.returns.IReturnsInteractor;
import ru.fitsme.android.presentation.fragments.base.BaseViewModel;

public class ReturnsViewModel extends BaseViewModel {

    @Inject
    IReturnsInteractor returnsInteractor;

    public ObservableField<String> showMessage;

    public ReturnsViewModel() {
        inject(this);
    }

    @Override
    protected void init() {
        showMessage = returnsInteractor.getShowMessage();
    }

    LiveData<PagedList<ReturnsOrder>> getPageLiveData() {
        return returnsInteractor.getPagedListLiveData();
    }

    LiveData<Boolean> getReturnsIsEmpty() {
        return returnsInteractor.getReturnsIsEmpty();
    }

    public void goToCheckout() {
        navigation.goToCheckout();
//        mainFragment.goToCheckout();
    }

    public void goToReturnsStepScreen() {
        switch (returnsInteractor.getReturnOrderStep()) {
            case 1:
                navigation.goToReturnsHowTo();
                return;
            case 2:
                navigation.goToReturnsChooseOrder();
                return;
            case 3:
                navigation.goToReturnsChooseItems(returnsInteractor.getReturnOrderId());
                return;
            case 4:
                navigation.goToReturnsIndicateNumber(returnsInteractor.getReturnId());
                return;
            case 5:
                navigation.goToReturnsBillingInfo(returnsInteractor.getReturnId());
                return;
            case 6:
                navigation.goToReturnsVerifyData(returnsInteractor.getReturnId());
                return;
            default:
                navigation.goToReturnsHowTo();
        }
    }

    public void goToReturnsHowTo() {
        navigation.goToReturnsHowTo();
    }

    public void goToReturnDetails(int returnId) {
        navigation.goToReturnDetails(returnId);
    }
}
