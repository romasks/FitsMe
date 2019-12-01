package ru.fitsme.android.presentation.fragments.returns.processing.two;

import androidx.databinding.ObservableBoolean;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import ru.fitsme.android.domain.entities.order.Order;
import ru.fitsme.android.domain.entities.returns.ReturnsItem;
import ru.fitsme.android.domain.interactors.orders.IOrdersInteractor;
import ru.fitsme.android.presentation.fragments.base.BaseViewModel;
import timber.log.Timber;

public class ChooseOrderReturnViewModel extends BaseViewModel {

    private final IOrdersInteractor ordersInteractor;

    public ObservableBoolean isLoading = new ObservableBoolean(true);
    private MutableLiveData<List<Order>> returnsOrdersLiveData = new MutableLiveData<>();
    private MutableLiveData<Boolean> returnsOrdersListIsEmpty = new MutableLiveData<>();

    public ChooseOrderReturnViewModel(@NotNull IOrdersInteractor ordersInteractor) {
        this.ordersInteractor = ordersInteractor;
        inject(this);
    }

    void init() {
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

    public void goToReturnsChooseItems(Order returnsOrder) {
        navigation.goToReturnsChooseItems(returnsOrder);
    }

    public void backToReturnsHowTo() {
        navigation.backToReturnsHowTo();
    }

    LiveData<Boolean> getReturnsOrdersIsEmpty() {
        return returnsOrdersListIsEmpty;
    }

    @Override
    public void onBackPressed() {
        navigation.goBack();
    }
}
