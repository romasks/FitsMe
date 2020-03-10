package ru.fitsme.android.presentation.fragments.returns.processing.two;

import java.util.List;

import javax.inject.Inject;

import androidx.databinding.ObservableBoolean;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import ru.fitsme.android.domain.entities.order.Order;
import ru.fitsme.android.domain.interactors.orders.IOrdersInteractor;
import ru.fitsme.android.domain.interactors.returns.IReturnsInteractor;
import ru.fitsme.android.presentation.fragments.base.BaseViewModel;
import ru.fitsme.android.utils.ReturnsOrderStep;
import timber.log.Timber;

public class ChooseOrderReturnViewModel extends BaseViewModel {

    @Inject
    IOrdersInteractor ordersInteractor;

    @Inject
    IReturnsInteractor returnsInteractor;

    public ObservableBoolean isLoading = new ObservableBoolean(true);
    private MutableLiveData<List<Order>> returnsOrdersLiveData = new MutableLiveData<>();
    private MutableLiveData<Boolean> returnsOrdersListIsEmpty = new MutableLiveData<>();

    public ChooseOrderReturnViewModel() {
        inject(this);
        returnsInteractor.setReturnOrderStep(ReturnsOrderStep.CHOOSE_ORDER);
    }

    @Override
    protected void init() {
        isLoading.set(true);
        returnsOrdersListIsEmpty.setValue(true);
        addDisposable(ordersInteractor.getReturnOrders()
                .subscribe(this::onGetResult, this::onError));
    }

    private void onError(Throwable throwable) {
        isLoading.set(false);
        Timber.tag(getClass().getName()).e(throwable);
    }

    private void onGetResult(List<Order> orders) {
        isLoading.set(false);
        returnsOrdersListIsEmpty.setValue(orders == null || orders.size() == 0);
        returnsOrdersLiveData.setValue(orders);
    }

    MutableLiveData<List<Order>> getReturnsOrdersLiveData() {
        return returnsOrdersLiveData;
    }

    public void goToReturnsChooseItems(int orderId) {
        returnsInteractor.setReturnOrderId(orderId);
        navigation.goToReturnsChooseItems();
    }

    LiveData<Boolean> getReturnsOrdersIsEmpty() {
        return returnsOrdersListIsEmpty;
    }

    public void goToCart() {
        navigation.goToCart();
    }
}
