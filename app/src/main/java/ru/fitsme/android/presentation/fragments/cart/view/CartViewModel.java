package ru.fitsme.android.presentation.fragments.cart.view;

import android.databinding.ObservableBoolean;

import org.jetbrains.annotations.NotNull;

import ru.fitsme.android.domain.entities.favourites.FavouritesItem;
import ru.fitsme.android.domain.interactors.orders.IOrdersInteractor;
import ru.fitsme.android.presentation.common.adapter.FavouritesAdapter;
import ru.fitsme.android.presentation.fragments.base.BaseViewModel;
import timber.log.Timber;

import static ru.fitsme.android.utils.Constants.GONE;

public class CartViewModel extends BaseViewModel {

    private IOrdersInteractor ordersInteractor;
    private FavouritesAdapter adapter;

    public ObservableBoolean loading;
    public ObservableBoolean showEmpty;

    public CartViewModel(@NotNull IOrdersInteractor ordersInteractor) {
        this.ordersInteractor = ordersInteractor;
    }

    void init() {
        loading = new ObservableBoolean(GONE);
        showEmpty = new ObservableBoolean(GONE);
        loadCart();
    }

    void setAdapter(int layoutId) {
        adapter = new FavouritesAdapter(layoutId, this);
    }

    FavouritesAdapter getAdapter() {
        return adapter;
    }

    private void loadCart() {
        addDisposable(
                ordersInteractor.getCurrentOrderInCart()
                        .subscribe(order -> {
                            Timber.tag(getClass().getName()).d("SUCCESS");
                            loading.set(GONE);
                        }, throwable -> {
                            Timber.tag(getClass().getName()).d("FAIL");
                        })
        );
    }

    public FavouritesItem getCartItemAt(int index) {
        return adapter.getFavouriteItemAt(index);
    }

}
