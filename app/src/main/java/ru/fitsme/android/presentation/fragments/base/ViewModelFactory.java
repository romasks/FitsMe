package ru.fitsme.android.presentation.fragments.base;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import org.jetbrains.annotations.NotNull;

import ru.fitsme.android.domain.interactors.BaseInteractor;
import ru.fitsme.android.domain.interactors.favourites.IFavouritesInteractor;
import ru.fitsme.android.domain.interactors.orders.IOrdersInteractor;
import ru.fitsme.android.presentation.fragments.cart.view.CartViewModel;
import ru.fitsme.android.presentation.fragments.checkout.CheckoutViewModel;
import ru.fitsme.android.presentation.fragments.favourites.view.FavouritesViewModel;

public class ViewModelFactory implements ViewModelProvider.Factory {

    private final BaseInteractor interactor;

    public ViewModelFactory(@NotNull BaseInteractor interactor) {
        this.interactor = interactor;
    }

    @NotNull
    @Override
    public <T extends ViewModel> T create(@NotNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(CartViewModel.class)) {
            return (T) new CartViewModel((IOrdersInteractor) interactor);
        } else
        if (modelClass.isAssignableFrom(CheckoutViewModel.class)) {
            return (T) new CheckoutViewModel((IOrdersInteractor) interactor);
        } else
        if (modelClass.isAssignableFrom(FavouritesViewModel.class)) {
            return (T) new FavouritesViewModel((IFavouritesInteractor) interactor);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
