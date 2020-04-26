package ru.fitsme.android.presentation.fragments.base;

import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;
import javax.inject.Singleton;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import ru.fitsme.android.presentation.fragments.auth.NumberViewModel;
import ru.fitsme.android.presentation.fragments.auth.CodeViewModel;
import ru.fitsme.android.presentation.fragments.cart.CartViewModel;
import ru.fitsme.android.presentation.fragments.checkout.CheckoutViewModel;
import ru.fitsme.android.presentation.fragments.favourites.FavouritesViewModel;
import ru.fitsme.android.presentation.fragments.feedback.FeedbackViewModel;
import ru.fitsme.android.presentation.fragments.filters.FiltersViewModel;
import ru.fitsme.android.presentation.fragments.iteminfo.ItemInfoViewModel;
import ru.fitsme.android.presentation.fragments.main.MainFragmentViewModel;
import ru.fitsme.android.presentation.fragments.orders.OrdersHistoryViewModel;
import ru.fitsme.android.presentation.fragments.orders.details.OrderDetailsViewModel;
import ru.fitsme.android.presentation.fragments.profile.viewmodel.MainProfileViewModel;
import ru.fitsme.android.presentation.fragments.profile.viewmodel.SizeProfileViewModel;
import ru.fitsme.android.presentation.fragments.rateItemsdetail.RateItemsDetailViewModel;
import ru.fitsme.android.presentation.fragments.rateitems.RateItemsViewModel;
import ru.fitsme.android.presentation.fragments.returns.ReturnsViewModel;
import ru.fitsme.android.presentation.fragments.returns.details.ReturnDetailsViewModel;
import ru.fitsme.android.presentation.fragments.returns.processing.five.BillingInfoReturnViewModel;
import ru.fitsme.android.presentation.fragments.returns.processing.four.IndicateNumberReturnViewModel;
import ru.fitsme.android.presentation.fragments.returns.processing.one.HowToReturnViewModel;
import ru.fitsme.android.presentation.fragments.returns.processing.six.VerifyDataReturnViewModel;
import ru.fitsme.android.presentation.fragments.returns.processing.three.ChooseItemReturnViewModel;
import ru.fitsme.android.presentation.fragments.returns.processing.two.ChooseOrderReturnViewModel;
import ru.fitsme.android.presentation.fragments.splash.SplashViewModel;

@Singleton
public class ViewModelFactory implements ViewModelProvider.Factory {

    @Inject
    public ViewModelFactory() {
    }

    @NotNull
    @Override
    public <T extends ViewModel> T create(@NotNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(CartViewModel.class)) {
            return (T) new CartViewModel();
        } else if (modelClass.isAssignableFrom(CheckoutViewModel.class)) {
            return (T) new CheckoutViewModel();
        } else if (modelClass.isAssignableFrom(FavouritesViewModel.class)) {
            return (T) new FavouritesViewModel();
        } else if (modelClass.isAssignableFrom(ItemInfoViewModel.class)) {
            return (T) new ItemInfoViewModel();
        } else if (modelClass.isAssignableFrom(RateItemsViewModel.class)) {
            return (T) new RateItemsViewModel();
        } else if (modelClass.isAssignableFrom(RateItemsDetailViewModel.class)) {
                return (T) new RateItemsDetailViewModel();
        } else if (modelClass.isAssignableFrom(SplashViewModel.class)) {
            return (T) new SplashViewModel();
        } else if (modelClass.isAssignableFrom(MainProfileViewModel.class)) {
            return (T) new MainProfileViewModel();
        } else if (modelClass.isAssignableFrom(SizeProfileViewModel.class)) {
            return (T) new SizeProfileViewModel();
        } else if (modelClass.isAssignableFrom(OrdersHistoryViewModel.class)) {
            return (T) new OrdersHistoryViewModel();
        } else if (modelClass.isAssignableFrom(OrderDetailsViewModel.class)) {
            return (T) new OrderDetailsViewModel();
        } else if (modelClass.isAssignableFrom(ReturnsViewModel.class)) {
            return (T) new ReturnsViewModel();
        } else if (modelClass.isAssignableFrom(ReturnDetailsViewModel.class)) {
            return (T) new ReturnDetailsViewModel();
        } else if (modelClass.isAssignableFrom(HowToReturnViewModel.class)) {
            return (T) new HowToReturnViewModel();
        } else if (modelClass.isAssignableFrom(ChooseOrderReturnViewModel.class)) {
            return (T) new ChooseOrderReturnViewModel();
        } else if (modelClass.isAssignableFrom(ChooseItemReturnViewModel.class)) {
            return (T) new ChooseItemReturnViewModel();
        } else if (modelClass.isAssignableFrom(IndicateNumberReturnViewModel.class)) {
            return (T) new IndicateNumberReturnViewModel();
        } else if (modelClass.isAssignableFrom(BillingInfoReturnViewModel.class)) {
            return (T) new BillingInfoReturnViewModel();
        } else if (modelClass.isAssignableFrom(VerifyDataReturnViewModel.class)) {
            return (T) new VerifyDataReturnViewModel();
        } else if (modelClass.isAssignableFrom(MainFragmentViewModel.class)) {
            return (T) new MainFragmentViewModel();
        } else if (modelClass.isAssignableFrom(FiltersViewModel.class)) {
            return (T) new FiltersViewModel();
        } else if (modelClass.isAssignableFrom(FeedbackViewModel.class)) {
            return (T) new FeedbackViewModel();
        } else if (modelClass.isAssignableFrom(NumberViewModel.class)) {
            return (T) new NumberViewModel();
        } else if (modelClass.isAssignableFrom(CodeViewModel.class)) {
            return (T) new CodeViewModel();
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
