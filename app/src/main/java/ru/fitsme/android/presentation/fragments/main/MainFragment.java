package ru.fitsme.android.presentation.fragments.main;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ru.fitsme.android.R;
import ru.fitsme.android.app.App;
import ru.fitsme.android.databinding.FragmentMainBinding;
import ru.fitsme.android.presentation.fragments.cart.CartFragment;
import ru.fitsme.android.presentation.fragments.checkout.CheckoutFragment;
import ru.fitsme.android.presentation.fragments.favourites.FavouritesFragment;
import ru.fitsme.android.presentation.fragments.profile.ProfileFragment;
import ru.fitsme.android.presentation.fragments.rateitems.RateItemsFragment;

public class MainFragment extends Fragment {

    private FragmentMainBinding binding;

    public MainFragment() {
        App.getInstance().getDi().inject(this);
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
        initBottomNavigation(view);
    }

    private void initBottomNavigation(View view) {
        binding.bnvMainFrNavigation.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.action_items:
                    switchFragment(RateItemsFragment.newInstance());
                    return true;
                case R.id.action_likes:
                    switchFragment(FavouritesFragment.newInstance());
                    return true;
                case R.id.action_cart:
                    switchFragment(CartFragment.newInstance());
                    return true;
                case R.id.action_profile:
                    switchFragment(ProfileFragment.newInstance());
                    return true;
            }
            return false;
        });
        binding.bnvMainFrNavigation.setSelectedItemId(R.id.action_items);
    }

    private void switchFragment(Fragment fragment) {
        getChildFragmentManager().beginTransaction()
                .replace(R.id.fragment_main_container, fragment)
                .commit();
    }

    public void goToFavourites() {
        binding.bnvMainFrNavigation.setSelectedItemId(R.id.action_likes);
    }

    public void goToCheckout() {
        switchFragment(CheckoutFragment.newInstance());
    }

    public void showBottomNavigation(boolean b){
        if (b){
            binding.bnvMainFrNavigation.setVisibility(View.VISIBLE);
        } else {
            binding.bnvMainFrNavigation.setVisibility(View.GONE);
        }
    }
}
