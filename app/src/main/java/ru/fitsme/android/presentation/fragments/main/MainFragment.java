package ru.fitsme.android.presentation.fragments.main;

import android.view.View;

import java.util.List;

import javax.inject.Inject;

import androidx.fragment.app.Fragment;
import ru.fitsme.android.R;
import ru.fitsme.android.app.App;
import ru.fitsme.android.databinding.FragmentMainBinding;
import ru.fitsme.android.presentation.fragments.base.BaseFragment;
import ru.terrakok.cicerone.Navigator;

import static ru.fitsme.android.presentation.fragments.main.MainSupportFragmentNavigator.getFragmentNavigator;

public class MainFragment extends BaseFragment<MainFragmentViewModel> {

    @Inject
    MainNavigation navigation;

    private FragmentMainBinding binding;
    private Navigator navigator;

    public MainFragment() {
        App.getInstance().getDi().inject(this);
        navigation.setNavigator(navigator);
    }

    @Override
    public void onBackPressed() {
        List<Fragment> list = getChildFragmentManager().getFragments();
        if (list.size() > 0) {
            BaseFragment fragment = (BaseFragment) list.get(list.size() - 1);
            fragment.onBackPressed();
        }
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_main;
    }

    @Override
    protected void afterCreateView(View view) {
        binding = FragmentMainBinding.bind(view);
        setUp();
    }

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    private void setUp() {
        navigator = getFragmentNavigator(this);
        initBottomNavigation();
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

    private void initBottomNavigation() {
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

    public void goToRateItems() {
        binding.bnvMainFrNavigation.setSelectedItemId(R.id.action_items);
    }

    public void showBottomNavigation(boolean isShow) {
        binding.bnvMainFrNavigation.setVisibility(isShow ? View.VISIBLE : View.GONE);
    }

    public void showBottomShadow(boolean isShow) {
        binding.fragmentMainBottomShadow.setVisibility(isShow ? View.VISIBLE : View.GONE);
    }

    public void hideBottomNavbar() {
        binding.bnvMainFrNavigation.setVisibility(View.GONE);
    }

    public void showBottomNavbar() {
        binding.bnvMainFrNavigation.setVisibility(View.VISIBLE);
    }
}
