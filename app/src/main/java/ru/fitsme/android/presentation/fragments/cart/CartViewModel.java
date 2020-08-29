package ru.fitsme.android.presentation.fragments.cart;

import java.util.List;

import javax.inject.Inject;

import androidx.databinding.ObservableField;
import androidx.databinding.ObservableInt;
import androidx.lifecycle.LiveData;
import androidx.paging.PagedList;
import io.reactivex.Single;
import ru.fitsme.android.domain.entities.order.OrderItem;
import ru.fitsme.android.domain.interactors.cart.ICartInteractor;
import ru.fitsme.android.domain.interactors.clothes.IClothesInteractor;
import ru.fitsme.android.domain.interactors.profile.IProfileInteractor;
import ru.fitsme.android.presentation.fragments.base.BaseViewModel;
import ru.fitsme.android.presentation.fragments.iteminfo.ClotheInfo;
import timber.log.Timber;

public class CartViewModel extends BaseViewModel {

    @Inject
    ICartInteractor cartInteractor;

    @Inject
    IClothesInteractor clothesInteractor;

    @Inject
    IProfileInteractor profileInteractor;

    public ObservableField<String> message;
    public ObservableInt totalPrice;
    private ObservableInt currentTopSizeIndex;
    private ObservableInt currentBottomSizeIndex;

    public CartViewModel() {
        inject(this);
    }

    public void init() {
        message = cartInteractor.getMessage();
        totalPrice = cartInteractor.getTotalPrice();
        profileInteractor.updateInfo();
        currentTopSizeIndex = profileInteractor.getCurrentTopSizeIndex();
        currentBottomSizeIndex = profileInteractor.getCurrentBottomSizeIndex();
    }

    public ObservableInt getCurrentTopSizeIndex() {
        return currentTopSizeIndex;
    }

    public ObservableInt getCurrentBottomSizeIndex() {
        return currentBottomSizeIndex;
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

    public void removeNoSizeItems(List<Integer> noSizeOrderItemsIds) {
        addDisposable(cartInteractor.removeItemsFromOrder(noSizeOrderItemsIds)
                .subscribe(this::onItemsRemoved, Timber::e));
    }

    private void onItemsRemoved(Integer integer) {
        cartInteractor.invalidateDataSource();
    }

    public LiveData<String> getCurrentTopSize() {
        return profileInteractor.getCurrentTopSize();
    }

    public LiveData<String> getCurrentBottomSize() {
        return profileInteractor.getCurrentBottomSize();
    }
}
