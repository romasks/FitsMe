package ru.fitsme.android.presentation.fragments.returns;

import javax.inject.Inject;

import androidx.databinding.ObservableField;
import androidx.lifecycle.LiveData;
import androidx.paging.PagedList;
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

    public void goToReturnsStepScreen(ReturnsOrder returnsOrder) {
        if (returnsOrder.getReturnItemsList().isEmpty()) {
            navigation.goToReturnsChooseOrder();
        } else if (returnsOrder.getDeliveryDetails().isEmpty()) {
            navigation.goToReturnsIndicateNumber();
        } else if (returnsOrder.getPaymentDetails().isEmpty()) {
            navigation.goToReturnsBillingInfo();
        } else if (returnsOrder.getStatus().equals("FM")) {
            navigation.goToReturnsVerifyData();
        } else {
            navigation.goToReturnsHowTo();
        }

        /*switch (returnsInteractor.getReturnOrderStep()) {
            case HOW_TO:
                navigation.goToReturnsHowTo();
                return;
            case CHOOSE_ORDER:
                navigation.goToReturnsChooseOrder();
                return;
            case CHOOSE_ITEMS:
                navigation.goToReturnsChooseItems();
                return;
            case INDICATE_NUMBER:
                navigation.goToReturnsIndicateNumber();
                return;
            case BILLING_INFO:
                navigation.goToReturnsBillingInfo();
                return;
            case VERIFY_DATA:
                navigation.goToReturnsVerifyData();
                return;
            default:
                navigation.goToReturnsHowTo();
        }*/
    }

    public void goToReturnsHowTo() {
        navigation.goToReturnsHowTo();
    }

    public void goToReturnDetails(int returnId) {
        navigation.goToReturnDetails(returnId);
    }
}
