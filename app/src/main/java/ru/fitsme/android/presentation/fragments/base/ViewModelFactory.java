package ru.fitsme.android.presentation.fragments.base;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import org.jetbrains.annotations.NotNull;

import ru.fitsme.android.domain.interactors.BaseInteractor;
import ru.fitsme.android.domain.interactors.auth.IAuthInteractor;
import ru.fitsme.android.domain.interactors.auth.ISignInteractor;
import ru.fitsme.android.domain.interactors.clothes.IClothesInteractor;
import ru.fitsme.android.domain.interactors.favourites.IFavouritesInteractor;
import ru.fitsme.android.domain.interactors.orders.IOrdersInteractor;
import ru.fitsme.android.domain.interactors.profile.IProfileInteractor;
import ru.fitsme.android.domain.interactors.returns.IReturnsInteractor;
import ru.fitsme.android.presentation.fragments.cart.CartViewModel;
import ru.fitsme.android.presentation.fragments.checkout.CheckoutViewModel;
import ru.fitsme.android.presentation.fragments.favourites.FavouritesViewModel;
import ru.fitsme.android.presentation.fragments.filters.FiltersViewModel;
import ru.fitsme.android.presentation.fragments.iteminfo.ItemInfoViewModel;
import ru.fitsme.android.presentation.fragments.main.MainFragmentViewModel;
import ru.fitsme.android.presentation.fragments.profile.viewmodel.MainProfileViewModel;
import ru.fitsme.android.presentation.fragments.profile.viewmodel.SizeProfileViewModel;
import ru.fitsme.android.presentation.fragments.rateitems.RateItemsViewModel;
import ru.fitsme.android.presentation.fragments.returns.ReturnsViewModel;
import ru.fitsme.android.presentation.fragments.returns.processing.five.BillingInfoReturnViewModel;
import ru.fitsme.android.presentation.fragments.returns.processing.four.IndicateNumberReturnViewModel;
import ru.fitsme.android.presentation.fragments.returns.processing.one.HowToReturnViewModel;
import ru.fitsme.android.presentation.fragments.returns.processing.six.VerifyDataReturnViewModel;
import ru.fitsme.android.presentation.fragments.returns.processing.three.ChooseItemReturnViewModel;
import ru.fitsme.android.presentation.fragments.returns.processing.two.ChooseOrderReturnViewModel;
import ru.fitsme.android.presentation.fragments.signinup.viewmodel.SignInUpViewModel;
import ru.fitsme.android.presentation.fragments.signinup.viewmodel.SignInViewModel;
import ru.fitsme.android.presentation.fragments.signinup.viewmodel.SignUpViewModel;
import ru.fitsme.android.presentation.fragments.splash.SplashViewModel;

public class ViewModelFactory implements ViewModelProvider.Factory {

    private final BaseInteractor interactor;

    public ViewModelFactory(BaseInteractor interactor) {
        this.interactor = interactor;
    }

    public ViewModelFactory() {
        this.interactor = null;
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
            return (T) new ItemInfoViewModel((IClothesInteractor) interactor);
        } else if (modelClass.isAssignableFrom(RateItemsViewModel.class)) {
            return (T) new RateItemsViewModel((IClothesInteractor) interactor);
        } else if (modelClass.isAssignableFrom(SignInViewModel.class)) {
            return (T) new SignInViewModel((ISignInteractor) interactor);
        } else if (modelClass.isAssignableFrom(SignInUpViewModel.class)) {
            return (T) new SignInUpViewModel((IAuthInteractor) interactor);
        } else if (modelClass.isAssignableFrom(SignUpViewModel.class)) {
            return (T) new SignUpViewModel((ISignInteractor) interactor);
        } else if (modelClass.isAssignableFrom(SplashViewModel.class)) {
            return (T) new SplashViewModel((IAuthInteractor) interactor);
        } else if (modelClass.isAssignableFrom(MainProfileViewModel.class)) {
            return (T) new MainProfileViewModel((IProfileInteractor) interactor);
        } else if (modelClass.isAssignableFrom(SizeProfileViewModel.class)) {
            return (T) new SizeProfileViewModel((IProfileInteractor) interactor);
        } else if (modelClass.isAssignableFrom(ReturnsViewModel.class)) {
            return (T) new ReturnsViewModel((IReturnsInteractor) interactor);
        } else if (modelClass.isAssignableFrom(HowToReturnViewModel.class)) {
            return (T) new HowToReturnViewModel();
        } else if (modelClass.isAssignableFrom(ChooseOrderReturnViewModel.class)) {
            return (T) new ChooseOrderReturnViewModel((IOrdersInteractor) interactor);
        } else if (modelClass.isAssignableFrom(ChooseItemReturnViewModel.class)) {
            return (T) new ChooseItemReturnViewModel((IReturnsInteractor) interactor);
        } else if (modelClass.isAssignableFrom(IndicateNumberReturnViewModel.class)) {
            return (T) new IndicateNumberReturnViewModel((IReturnsInteractor) interactor);
        } else if (modelClass.isAssignableFrom(BillingInfoReturnViewModel.class)) {
            return (T) new BillingInfoReturnViewModel((IReturnsInteractor) interactor);
        } else if (modelClass.isAssignableFrom(VerifyDataReturnViewModel.class)) {
            return (T) new VerifyDataReturnViewModel((IReturnsInteractor) interactor);
        } else if (modelClass.isAssignableFrom(MainFragmentViewModel.class)) {
            return (T) new MainFragmentViewModel();
        } else if (modelClass.isAssignableFrom(FiltersViewModel.class)) {
            return (T) new FiltersViewModel();
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
