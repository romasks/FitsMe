package ru.fitsme.android.presentation.fragments.main;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import javax.inject.Inject;

import ru.fitsme.android.R;
import ru.fitsme.android.app.App;
import ru.fitsme.android.databinding.FragmentMainBinding;
import ru.fitsme.android.presentation.fragments.cart.CartFragment;
import ru.fitsme.android.presentation.fragments.checkout.CheckoutFragment;
import ru.fitsme.android.presentation.fragments.favourites.FavouritesFragment;
import ru.fitsme.android.presentation.fragments.profile.view.MainProfileFragment;
import ru.fitsme.android.presentation.fragments.profile.view.SizeProfileFragment;
import ru.fitsme.android.presentation.fragments.rateitems.RateItemsFragment;
import ru.fitsme.android.presentation.fragments.returns.ReturnsFragment;
import ru.fitsme.android.presentation.fragments.returns.processing.first.HowToReturnFragment;
import ru.terrakok.cicerone.Navigator;
import ru.terrakok.cicerone.android.SupportFragmentNavigator;

import static ru.fitsme.android.presentation.fragments.main.MainNavigation.NAV_CART;
import static ru.fitsme.android.presentation.fragments.main.MainNavigation.NAV_CHECKOUT;
import static ru.fitsme.android.presentation.fragments.main.MainNavigation.NAV_FAVOURITES;
import static ru.fitsme.android.presentation.fragments.main.MainNavigation.NAV_MAIN_PROFILE;
import static ru.fitsme.android.presentation.fragments.main.MainNavigation.NAV_ORDER_HISTORY_PROFILE;
import static ru.fitsme.android.presentation.fragments.main.MainNavigation.NAV_ORDER_RETURN_PROFILE;
import static ru.fitsme.android.presentation.fragments.main.MainNavigation.NAV_RATE_ITEMS;
import static ru.fitsme.android.presentation.fragments.main.MainNavigation.NAV_RETURNS_BILLING_INFO;
import static ru.fitsme.android.presentation.fragments.main.MainNavigation.NAV_RETURNS_CHOOSE_ITEMS;
import static ru.fitsme.android.presentation.fragments.main.MainNavigation.NAV_RETURNS_CHOOSE_ORDER;
import static ru.fitsme.android.presentation.fragments.main.MainNavigation.NAV_RETURNS_HOW_TO;
import static ru.fitsme.android.presentation.fragments.main.MainNavigation.NAV_RETURNS_INDICATE_NUMBER;
import static ru.fitsme.android.presentation.fragments.main.MainNavigation.NAV_RETURNS_VERIFY_DATA;
import static ru.fitsme.android.presentation.fragments.main.MainNavigation.NAV_SIZE_PROFILE;
import static ru.fitsme.android.presentation.fragments.main.MainNavigation.NAV_TYPE_PROFILE;

public class MainFragment extends Fragment {

    private FragmentMainBinding binding;
    @Inject
    MainNavigation navigation;
    private Navigator navigator;

    public MainFragment() {
        App.getInstance().getDi().inject(this);
        navigation.setNavigator(navigator);
    }

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navigator  = getFragmentNavigator();
        initBottomNavigation(view);
    }

    @Override
    public void onResume() {
        super.onResume();
        navigation.setNavigator(navigator);
    }

    @Override
    public void onPause() {
        super.onPause();
        navigation.removeNavigator();
    }

    private void initBottomNavigation(View view) {
        binding.bnvMainFrNavigation.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.action_items:
                    navigation.goToRateItems();
                    return true;
                case R.id.action_likes:
                    navigation.goToFavourites();
                    return true;
                case R.id.action_cart:
                    navigation.goToCart();
                    return true;
                case R.id.action_profile:
                    navigation.goToMainProfile();
                    return true;
            }
            return false;
        });
        binding.bnvMainFrNavigation.setSelectedItemId(R.id.action_items);
    }

    public void goToFavourites() {
        binding.bnvMainFrNavigation.setSelectedItemId(R.id.action_likes);
    }

    public void goToRateItems(){
        binding.bnvMainFrNavigation.setSelectedItemId(R.id.action_items);
    }

    public void goToMainProfile(){
        navigation.goToMainProfile();
    }

    public void goToCheckout() {
        binding.bnvMainFrNavigation.setSelectedItemId(R.id.action_cart);
        navigation.goToCheckout();
    }

    public void showBottomNavigation(boolean b){
        if (b){
            binding.bnvMainFrNavigation.setVisibility(View.VISIBLE);
        } else {
            binding.bnvMainFrNavigation.setVisibility(View.GONE);
        }
    }

    public int getBottomNavigationSize(){
        return binding.bnvMainFrNavigation.getHeight();
    }

    public void showBottomShadow(boolean b){
        if (b){
            binding.fragmentMainBottomShadow.setVisibility(View.VISIBLE);
        } else {
            binding.fragmentMainBottomShadow.setVisibility(View.GONE);
        }
    }

    @NonNull
    private SupportFragmentNavigator getFragmentNavigator() {
        return new SupportFragmentNavigator(getChildFragmentManager(), R.id.fragment_main_container) {
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
                        break;
                    case NAV_ORDER_RETURN_PROFILE:
                        return ReturnsFragment.newInstance();
                    case NAV_RETURNS_HOW_TO:
                        return HowToReturnFragment.newInstance();
                    case NAV_RETURNS_CHOOSE_ORDER:
                        break;
                    case NAV_RETURNS_CHOOSE_ITEMS:
                        break;
                    case NAV_RETURNS_INDICATE_NUMBER:
                        break;
                    case NAV_RETURNS_BILLING_INFO:
                        break;
                    case NAV_RETURNS_VERIFY_DATA:
                        break;
                }
                throw new RuntimeException("Unknown screen key");
            }

            @Override
            protected void showSystemMessage(String message) {
                Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
            }

            @Override
            protected void exit() {
                getActivity().finish();
            }
        };
    }
}
