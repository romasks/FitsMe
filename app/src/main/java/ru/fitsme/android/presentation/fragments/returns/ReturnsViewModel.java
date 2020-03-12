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

    public void goToReturnsStepScreen(ReturnsOrder returnsOrder) {
        if (returnsOrder.getReturnItemsList().isEmpty()) {
            navigation.goToReturnsChooseOrder();
        } else if (returnsOrder.getDeliveryDetails() == null || returnsOrder.getDeliveryDetails().isEmpty()) {
            navigation.goToReturnsIndicateNumber(returnsOrder.getId());
        } else if (returnsOrder.getPaymentDetails() == null || returnsOrder.getPaymentDetails().isEmpty()) {
            navigation.goToReturnsBillingInfo(returnsOrder.getId());
        } else if (returnsOrder.getStatus().equals("FM")) {
            navigation.goToReturnsVerifyData(returnsOrder.getId());
        } else {
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
