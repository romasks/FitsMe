package ru.fitsme.android.presentation.fragments.returns.processing.two;

import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;
import androidx.lifecycle.LiveData;
import androidx.paging.PagedList;

import javax.inject.Inject;

import ru.fitsme.android.domain.entities.order.Order;
import ru.fitsme.android.domain.interactors.returns.IReturnsInteractor;
import ru.fitsme.android.presentation.fragments.base.BaseViewModel;

public class ChooseOrderReturnViewModel extends BaseViewModel {

    @Inject
    IReturnsInteractor returnsInteractor;

    public ObservableBoolean isLoading = new ObservableBoolean(true);
    public ObservableField<String> message;

    public ChooseOrderReturnViewModel() {
        inject(this);
    }

    @Override
    protected void init() {
        message = returnsInteractor.getShowMessage();
    }

    @Override
    public void onBackPressed() {
        navigation.goToReturnsHowToWithReplace();
    }

    LiveData<PagedList<Order>> getReturnsOrdersLiveData() {
        return returnsInteractor.getReturnOrdersPagedListLiveData();
    }

    LiveData<Boolean> getReturnsOrdersIsEmpty() {
        return returnsInteractor.getReturnOrdersIsEmpty();
    }

    public void goToReturnsChooseItems(int orderId) {
        navigation.goToReturnsChooseItemsWithReplace(orderId);
    }
}
