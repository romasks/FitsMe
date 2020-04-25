package ru.fitsme.android.presentation.fragments.checkout;

import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import ru.fitsme.android.data.frameworks.retrofit.entities.OrderRequest;
import ru.fitsme.android.data.models.OrderModel;
import ru.fitsme.android.domain.entities.clothes.ClothesItem;
import ru.fitsme.android.domain.entities.order.Order;
import ru.fitsme.android.domain.entities.order.OrderItem;
import ru.fitsme.android.domain.interactors.cart.ICartInteractor;
import ru.fitsme.android.domain.interactors.clothes.IClothesInteractor;
import ru.fitsme.android.presentation.fragments.base.BaseViewModel;
import ru.fitsme.android.utils.OrderStatus;
import timber.log.Timber;

public class CheckoutViewModel extends BaseViewModel {

    @Inject
    ICartInteractor cartInteractor;

    @Inject
    IClothesInteractor clothesInteractor;

    private MutableLiveData<Order> orderLiveData;
    private MutableLiveData<Boolean> successMakeOrderLiveData;
    private MutableLiveData<Boolean> noSizeItemsRemovedLiveData;

    public ObservableBoolean isLoading;
    public ObservableField<OrderModel> orderModel;

    public CheckoutViewModel() {
        inject(this);
    }

    LiveData<Order> getOrderLiveData() {
        return orderLiveData;
    }

    LiveData<Boolean> getSuccessMakeOrderLiveData() {
        return successMakeOrderLiveData;
    }

    LiveData<Boolean> getNoSizeItemsRemovedLiveData() {
        return noSizeItemsRemovedLiveData;
    }

    public LiveData<Boolean> getIsNeedShowSizeDialogForTop() {
        return clothesInteractor.getIsNeedShowSizeDialogForTop();
    }

    public void setIsNeedShowSizeDialogForTop(Boolean flag){
        clothesInteractor.setIsNeedShowSizeDialogForTop(flag);
    }

    public LiveData<Boolean> getIsNeedShowSizeDialogForBottom() {
        return clothesInteractor.getIsNeedShowSizeDialogForBottom();
    }

    public void setIsNeedShowSizeDialogForBottom(Boolean flag){
        clothesInteractor.setIsNeedShowSizeDialogForBottom(flag);
    }

    public void onClickMakeOrder() {
        addDisposable(cartInteractor.makeOrder(new OrderRequest(orderModel.get()))
                .subscribe(this::onMakeOrder, Timber::e));
    }

    @Override
    protected void init() {
        orderLiveData = new MutableLiveData<>(null);
        successMakeOrderLiveData = new MutableLiveData<>(false);
        noSizeItemsRemovedLiveData = new MutableLiveData<>(false);
        isLoading = cartInteractor.getCheckOutIsLoading();
        orderModel = new ObservableField<>();
        loadOrder();
    }

    private void loadOrder() {
        addDisposable(cartInteractor.getSingleOrder(OrderStatus.FM)
                .subscribe(this::onOrder, Timber::e));
    }

    private void onOrder(@NotNull Order order) {
        orderLiveData.setValue(order);
        if (order.getOrderId() != 0) {
            orderModel.set(new OrderModel(order));
        }
    }

    private void onMakeOrder(Order order) {
        if (order.getOrderId() != 0) {
            Timber.tag(getClass().getName()).d("SUCCESS");
            successMakeOrderLiveData.setValue(true);
        }
    }

    public void removeNoSizeItems() {
        addDisposable(cartInteractor.getSingleOrder(OrderStatus.FM)
                .subscribe(this::onCheckItemsForRemove, Timber::e));
    }

    private void onCheckItemsForRemove(Order order) {
        List<Integer> noSizeOrderItemsIds = new ArrayList<>();
        for (OrderItem item : order.getOrderItemList()) {
            if (item.getClothe().getSizeInStock() == ClothesItem.SizeInStock.NO) {
                noSizeOrderItemsIds.add(item.getId());
            }
        }
        addDisposable(cartInteractor.removeItemsFromOrder(noSizeOrderItemsIds)
                .subscribe(this::onItemsRemoved, this::onItemsRemovedError));
    }

    private void onItemsRemoved(Integer integer) {

    }

    private void onItemsRemovedError(Throwable throwable) {
        Timber.tag(getClass().getName()).e("ERROR");
    }
}
