package ru.fitsme.android.presentation.fragments.cart.view;

import org.jetbrains.annotations.NotNull;

import ru.fitsme.android.domain.entities.favourites.FavouritesItem;
import ru.fitsme.android.domain.interactors.orders.IOrdersInteractor;
import ru.fitsme.android.presentation.common.base.BaseViewModel;
import timber.log.Timber;

public class CartViewModel extends BaseViewModel {

    private IOrdersInteractor ordersInteractor;

    public CartViewModel(@NotNull IOrdersInteractor ordersInteractor) {
        this.ordersInteractor = ordersInteractor;
    }

    void init() {
        Timber.tag(getClass().getName()).d("INIT Cart VM");
        loadCart();
    }

    private void loadCart() {
        addDisposable(
                ordersInteractor.getCurrentOrderInCart()
                        .subscribe(order -> {
                            Timber.tag(getClass().getName()).d("SUCCESS");
                        }, throwable -> {
                            Timber.tag(getClass().getName()).d("FAIL");
                        })
        );
    }

    public boolean showEmpty() {
        return true;
    }

    public boolean loading() {
        return true;
    }

    public FavouritesItem getCartItemAt(int position) {
        return new FavouritesItem();
    }

}
