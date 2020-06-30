package ru.fitsme.android.presentation.fragments.main;

import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import ru.fitsme.android.R;
import ru.fitsme.android.domain.entities.order.Order;
import ru.fitsme.android.presentation.fragments.auth.CodeFragment;
import ru.fitsme.android.presentation.fragments.cart.CartFragment;
import ru.fitsme.android.presentation.fragments.checkout.CheckoutFragment;
import ru.fitsme.android.presentation.fragments.favourites.FavouritesFragment;
import ru.fitsme.android.presentation.fragments.feedback.FeedbackFragment;
import ru.fitsme.android.presentation.fragments.filters.FiltersFragment;
import ru.fitsme.android.presentation.fragments.iteminfo.ItemInfoFragment;
import ru.fitsme.android.presentation.fragments.orders.OrdersHistoryFragment;
import ru.fitsme.android.presentation.fragments.orders.details.OrderDetailsFragment;
import ru.fitsme.android.presentation.fragments.profile.view.MainProfileFragment;
import ru.fitsme.android.presentation.fragments.profile.view.SizeProfileFragment;
import ru.fitsme.android.presentation.fragments.rateitems.RateItemsFragment;
import ru.fitsme.android.presentation.fragments.returns.ReturnsFragment;
import ru.fitsme.android.presentation.fragments.returns.details.ReturnDetailsFragment;
import ru.fitsme.android.presentation.fragments.returns.processing.five.BillingInfoReturnFragment;
import ru.fitsme.android.presentation.fragments.returns.processing.four.IndicateNumberReturnFragment;
import ru.fitsme.android.presentation.fragments.returns.processing.one.HowToReturnFragment;
import ru.fitsme.android.presentation.fragments.returns.processing.six.VerifyDataReturnFragment;
import ru.fitsme.android.presentation.fragments.returns.processing.three.ChooseItemReturnFragment;
import ru.fitsme.android.presentation.fragments.returns.processing.two.ChooseOrderReturnFragment;
import ru.terrakok.cicerone.android.SupportFragmentNavigator;

import static ru.fitsme.android.presentation.fragments.main.MainNavigation.NAV_CART;
import static ru.fitsme.android.presentation.fragments.main.MainNavigation.NAV_CHECKOUT;
import static ru.fitsme.android.presentation.fragments.main.MainNavigation.NAV_CODE;
import static ru.fitsme.android.presentation.fragments.main.MainNavigation.NAV_DETAIL_ITEM_INFO;
import static ru.fitsme.android.presentation.fragments.main.MainNavigation.NAV_FAVOURITES;
import static ru.fitsme.android.presentation.fragments.main.MainNavigation.NAV_FILTER;
import static ru.fitsme.android.presentation.fragments.main.MainNavigation.NAV_LEAVE_FEEDBACK;
import static ru.fitsme.android.presentation.fragments.main.MainNavigation.NAV_MAIN_PROFILE;
import static ru.fitsme.android.presentation.fragments.main.MainNavigation.NAV_ORDER_DETAILS;
import static ru.fitsme.android.presentation.fragments.main.MainNavigation.NAV_ORDER_HISTORY_PROFILE;
import static ru.fitsme.android.presentation.fragments.main.MainNavigation.NAV_ORDER_RETURN_PROFILE;
import static ru.fitsme.android.presentation.fragments.main.MainNavigation.NAV_RATE_ITEMS;
import static ru.fitsme.android.presentation.fragments.main.MainNavigation.NAV_RETURNS_BILLING_INFO;
import static ru.fitsme.android.presentation.fragments.main.MainNavigation.NAV_RETURNS_CHOOSE_ITEMS;
import static ru.fitsme.android.presentation.fragments.main.MainNavigation.NAV_RETURNS_CHOOSE_ORDER;
import static ru.fitsme.android.presentation.fragments.main.MainNavigation.NAV_RETURNS_HOW_TO;
import static ru.fitsme.android.presentation.fragments.main.MainNavigation.NAV_RETURNS_INDICATE_NUMBER;
import static ru.fitsme.android.presentation.fragments.main.MainNavigation.NAV_RETURNS_VERIFY_DATA;
import static ru.fitsme.android.presentation.fragments.main.MainNavigation.NAV_RETURN_DETAILS;
import static ru.fitsme.android.presentation.fragments.main.MainNavigation.NAV_SIZE_PROFILE;
import static ru.fitsme.android.presentation.fragments.main.MainNavigation.NAV_TYPE_PROFILE;

public class MainSupportFragmentNavigator {

    @NonNull
    public static SupportFragmentNavigator getFragmentNavigator(Fragment fragment) {
        return new SupportFragmentNavigator(fragment.getChildFragmentManager(), R.id.fragment_main_container) {
            @Override
            protected Fragment createFragment(String screenKey, Object data) {
                switch (screenKey) {
                    case NAV_RATE_ITEMS:
                        return RateItemsFragment.newInstance();
                    case NAV_FAVOURITES:
                        return FavouritesFragment.newInstance();
                    case NAV_CART:
                        return CartFragment.newInstance();
                    case NAV_CHECKOUT:
                        return CheckoutFragment.newInstance();
                    case NAV_MAIN_PROFILE:
                        return MainProfileFragment.newInstance();
                    case NAV_SIZE_PROFILE:
                        return SizeProfileFragment.newInstance();
                    case NAV_TYPE_PROFILE:
                        break;
                    case NAV_ORDER_HISTORY_PROFILE:
                        return OrdersHistoryFragment.newInstance();
                    case NAV_ORDER_RETURN_PROFILE:
                        return ReturnsFragment.newInstance();
                    case NAV_ORDER_DETAILS:
                        return OrderDetailsFragment.newInstance((Order) data);
                    case NAV_LEAVE_FEEDBACK:
                        return FeedbackFragment.newInstance();
                    case NAV_RETURN_DETAILS:
                        return ReturnDetailsFragment.newInstance((int) data);
                    case NAV_RETURNS_HOW_TO:
                        return HowToReturnFragment.newInstance();
                    case NAV_RETURNS_CHOOSE_ORDER:
                        return ChooseOrderReturnFragment.newInstance();
                    case NAV_RETURNS_CHOOSE_ITEMS:
                        return ChooseItemReturnFragment.newInstance((int) data);
                    case NAV_RETURNS_INDICATE_NUMBER:
                        return IndicateNumberReturnFragment.newInstance((int) data);
                    case NAV_RETURNS_BILLING_INFO:
                        return BillingInfoReturnFragment.newInstance((int) data);
                    case NAV_RETURNS_VERIFY_DATA:
                        return VerifyDataReturnFragment.newInstance((int) data);
                    case NAV_DETAIL_ITEM_INFO:
                        return ItemInfoFragment.newInstance(data);
                    case NAV_FILTER:
                        return FiltersFragment.newInstance();
                    case NAV_CODE:
                        return CodeFragment.newInstance();
                }
                throw new RuntimeException("Unknown screen key");
            }

            @Override
            protected void showSystemMessage(String message) {
                Toast.makeText(fragment.getActivity(), message, Toast.LENGTH_SHORT).show();
            }

            @Override
            protected void exit() {
                if (fragment.getActivity() != null) fragment.getActivity().finish();
            }
        };
    }
}
