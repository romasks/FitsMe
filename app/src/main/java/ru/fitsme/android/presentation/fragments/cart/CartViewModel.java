package ru.fitsme.android.presentation.fragments.cart;

import androidx.databinding.ObservableField;
import androidx.databinding.ObservableInt;
import androidx.lifecycle.LiveData;
import androidx.paging.PagedList;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;
import ru.fitsme.android.domain.entities.clothes.ClothesItem;
import ru.fitsme.android.domain.entities.order.OrderItem;
import ru.fitsme.android.domain.interactors.cart.ICartInteractor;
import ru.fitsme.android.domain.interactors.clothes.IClothesInteractor;
import ru.fitsme.android.presentation.fragments.base.BaseViewModel;
import ru.fitsme.android.presentation.fragments.iteminfo.ClotheInfo;
import timber.log.Timber;

public class CartViewModel extends BaseViewModel {

    @Inject
    ICartInteractor cartInteractor;

    @Inject
    IClothesInteractor clothesInteractor;

    public ObservableField<String> message;
    public ObservableInt totalPrice;

    public CartViewModel() {
        inject(this);
    }

    public void init() {
        message = cartInteractor.getMessage();
        totalPrice = cartInteractor.getTotalPrice();
    }

    public LiveData<Boolean> isNeedShowSizeDialogForTop() {
        return clothesInteractor.getIsNeedShowSizeDialogForTop();
    }

    public void setIsNeedShowSizeDialogForTop(Boolean flag) {
        clothesInteractor.setIsNeedShowSizeDialogForTop(flag);
    }

    public LiveData<Boolean> isNeedShowSizeDialogForBottom() {
        return clothesInteractor.getIsNeedShowSizeDialogForBottom();
    }

    public void setIsNeedShowSizeDialogForBottom(Boolean flag) {
        clothesInteractor.setIsNeedShowSizeDialogForBottom(flag);
    }

    LiveData<PagedList<OrderItem>> getPageLiveData() {
        return cartInteractor.getPagedListLiveData();
    }

    LiveData<Boolean> getCartIsEmpty() {
        return cartInteractor.getCartIsEmpty();
    }

    public Single<OrderItem> removeItemFromOrder(int position) {
        return cartInteractor.removeItemFromOrder(position);
    }

    Single<OrderItem> restoreItemToOrder(int position) {
        return cartInteractor.restoreItemToOrder(position);
    }

    boolean itemIsRemoved(int position) {
        return cartInteractor.itemIsRemoved(position);
    }

    @Override
    public void onBackPressed() {
        navigation.finish();
    }

    public void setDetailView(ClotheInfo clotheInfo) {
        navigation.goToDetailItemInfo(clotheInfo);
    }

    public void goToCheckout() {
        navigation.goToCheckout();
    }

    public void updateList() {
        cartInteractor.updateList();
    }

    public void removeNoSizeItems(List<OrderItem> orderItemsList) {
        List<Integer> noSizeOrderItemsIds = new ArrayList<>();
        for (OrderItem item : orderItemsList) {
            if (item.getClothe().getSizeInStock() == ClothesItem.SizeInStock.NO) {
                noSizeOrderItemsIds.add(item.getId());
            }
        }
        addDisposable(cartInteractor.removeItemsFromOrder(noSizeOrderItemsIds)
                .subscribe(this::onItemsRemoved, Timber::e));
    }

    private void onItemsRemoved(Integer integer) {
        cartInteractor.invalidateDataSource();
    }
}
