package ru.fitsme.android.presentation.fragments.base;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import org.jetbrains.annotations.NotNull;

import ru.fitsme.android.domain.interactors.BaseInteractor;
import ru.fitsme.android.domain.interactors.auth.ISignInUpInteractor;
import ru.fitsme.android.domain.interactors.favourites.IFavouritesInteractor;
import ru.fitsme.android.domain.interactors.orders.IOrdersInteractor;
import ru.fitsme.android.presentation.fragments.cart.CartViewModel;
import ru.fitsme.android.presentation.fragments.checkout.CheckoutViewModel;
import ru.fitsme.android.presentation.fragments.favourites.FavouritesViewModel;
import ru.fitsme.android.presentation.fragments.signinup.viewmodel.SignInUpViewModel;
import ru.fitsme.android.presentation.fragments.signinup.viewmodel.SignInViewModel;
import ru.fitsme.android.presentation.fragments.signinup.viewmodel.SignUpViewModel;

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
        } else
        if (modelClass.isAssignableFrom(SignInViewModel.class)) {
            return (T) new SignInViewModel((ISignInUpInteractor) interactor);
        } else
        if (modelClass.isAssignableFrom(SignInUpViewModel.class)) {
            return (T) new SignInUpViewModel((ISignInUpInteractor) interactor);
        } else
        if (modelClass.isAssignableFrom(SignUpViewModel.class)) {
            return (T) new SignUpViewModel((ISignInUpInteractor) interactor);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
