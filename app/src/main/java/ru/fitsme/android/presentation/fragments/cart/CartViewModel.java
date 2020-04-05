package ru.fitsme.android.presentation.fragments.cart;

import androidx.databinding.ObservableField;
import androidx.databinding.ObservableInt;
import androidx.lifecycle.LiveData;
import androidx.paging.PagedList;

import javax.inject.Inject;

import io.reactivex.Single;
import ru.fitsme.android.domain.entities.order.OrderItem;
import ru.fitsme.android.domain.interactors.clothes.IClothesInteractor;
import ru.fitsme.android.domain.interactors.orders.IOrdersInteractor;
import ru.fitsme.android.presentation.fragments.base.BaseViewModel;

public class CartViewModel extends BaseViewModel {

    @Inject
    IOrdersInteractor ordersInteractor;

    @Inject
    IClothesInteractor clothesInteractor;

    public ObservableField<String> message;
    public ObservableInt totalPrice;

    private LiveData<Boolean> isNeedShowSizeDialogForTop;
    private LiveData<Boolean> isNeedShowSizeDialogForBottom;

    public CartViewModel() {
        inject(this);
    }

    public void init() {
        message = ordersInteractor.getMessage();
        totalPrice = ordersInteractor.getTotalPrice();
        isNeedShowSizeDialogForTop = clothesInteractor.getIsNeedShowSizeDialogForTop();
        isNeedShowSizeDialogForBottom = clothesInteractor.getIsNeedShowSizeDialogForBottom();
    }

    public LiveData<Boolean> getIsNeedShowSizeDialogForTop() {
        return isNeedShowSizeDialogForTop;
    }

    public void setIsNeedShowSizeDialogForTop(Boolean flag){
        clothesInteractor.setIsNeedShowSizeDialogForTop(flag);
    }

    public LiveData<Boolean> getIsNeedShowSizeDialogForBottom() {
        return isNeedShowSizeDialogForBottom;
    }

    public void setIsNeedShowSizeDialogForBottom(Boolean flag){
        clothesInteractor.setIsNeedShowSizeDialogForBottom(flag);
    }

    LiveData<PagedList<OrderItem>> getPageLiveData() {
        return ordersInteractor.getPagedListLiveData();
    }

    LiveData<Boolean> getCartIsEmpty() {
        return ordersInteractor.getCartIsEmpty();
    }

    public Single<OrderItem> removeItemFromOrder(int position) {
        return ordersInteractor.removeItemFromOrder(position);
    }

    Single<OrderItem> restoreItemToOrder(int position) {
        return ordersInteractor.restoreItemToOrder(position);
    }

    boolean itemIsRemoved(int position) {
        return ordersInteractor.itemIsRemoved(position);
    }

    @Override
    public void onBackPressed() {
        navigation.finish();
    }

    public void setDetailView(OrderItem orderItem) {
        navigation.goToDetailItemInfo(orderItem.getClothe());
    }

    public void goToCheckout() {
        navigation.goToCheckout();
    }

    public void goToFavourites() {
        navigation.goToFavourites();
    }

    public void goToRateItems() {
        navigation.goToRateItems();
    }

    public void updateList() {
        ordersInteractor.updateList();
    }
}
