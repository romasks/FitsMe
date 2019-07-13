package ru.fitsme.android.presentation.fragments.base;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import org.jetbrains.annotations.NotNull;

import ru.fitsme.android.domain.interactors.BaseInteractor;
import ru.fitsme.android.domain.interactors.auth.IAuthInteractor;
import ru.fitsme.android.domain.interactors.clothes.IClothesInteractor;
import ru.fitsme.android.domain.interactors.favourites.IFavouritesInteractor;
import ru.fitsme.android.domain.interactors.orders.IOrdersInteractor;
import ru.fitsme.android.presentation.fragments.cart.CartViewModel;
import ru.fitsme.android.presentation.fragments.checkout.CheckoutViewModel;
import ru.fitsme.android.presentation.fragments.favourites.FavouritesViewModel;
import ru.fitsme.android.presentation.fragments.iteminfo.ItemInfoViewModel;
import ru.fitsme.android.presentation.fragments.rateitems.RateItemsViewModel;
import ru.fitsme.android.presentation.fragments.signinup.viewmodel.SignInUpViewModel;
import ru.fitsme.android.presentation.fragments.signinup.viewmodel.SignInViewModel;
import ru.fitsme.android.presentation.fragments.signinup.viewmodel.SignUpViewModel;
import ru.fitsme.android.presentation.fragments.splash.SplashViewModel;

public class ViewModelFactory implements ViewModelProvider.Factory {

    private final BaseInteractor interactor;
    private final Integer index;

    public ViewModelFactory(@NotNull BaseInteractor interactor) {
        this.interactor = interactor;
        this.index = null;
    }

    public ViewModelFactory(@NotNull BaseInteractor interactor, Integer index) {
        this.interactor = interactor;
        this.index = index;
    }

    @NotNull
    @Override
    public <T extends ViewModel> T create(@NotNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(CartViewModel.class)) {
            return (T) new CartViewModel((IOrdersInteractor) interactor);
        } else if (modelClass.isAssignableFrom(CheckoutViewModel.class)) {
            return (T) new CheckoutViewModel((IOrdersInteractor) interactor);
        } else if (modelClass.isAssignableFrom(FavouritesViewModel.class)) {
            return (T) new FavouritesViewModel((IFavouritesInteractor) interactor);
        } else if (modelClass.isAssignableFrom(ItemInfoViewModel.class)) {
            return (T) new ItemInfoViewModel((IClothesInteractor) interactor, index);
        } else if (modelClass.isAssignableFrom(RateItemsViewModel.class)) {
            return (T) new RateItemsViewModel((IClothesInteractor) interactor);
        } else if (modelClass.isAssignableFrom(SignInViewModel.class)) {
            return (T) new SignInViewModel((IAuthInteractor) interactor);
        } else if (modelClass.isAssignableFrom(SignInUpViewModel.class)) {
            return (T) new SignInUpViewModel((IAuthInteractor) interactor);
        } else if (modelClass.isAssignableFrom(SignUpViewModel.class)) {
            return (T) new SignUpViewModel((IAuthInteractor) interactor);
        } else if (modelClass.isAssignableFrom(SplashViewModel.class)) {
            return (T) new SplashViewModel((IAuthInteractor) interactor);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
