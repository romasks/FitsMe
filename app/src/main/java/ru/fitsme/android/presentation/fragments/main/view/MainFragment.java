package ru.fitsme.android.presentation.fragments.main.view;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import ru.fitsme.android.R;
import ru.fitsme.android.presentation.fragments.basket.BasketFragment;
import ru.fitsme.android.presentation.fragments.main.adapter.MainViewPageAdapter;
import ru.fitsme.android.presentation.fragments.profile.view.ProfileFragment;
import ru.fitsme.android.presentation.fragments.rateitems.view.RateItemsFragment;
import timber.log.Timber;


public class MainFragment extends Fragment {
    private BottomNavigationView bottomNavigationView;
    private ViewPager viewPager;

    public MainFragment() {
    }

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_rate_item, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initBottomNavigation(view);
        initViewPager(view);
    }

    private void initViewPager(View view) {
        viewPager = view.findViewById(R.id.vp_main_fr_container);
        viewPager.setAdapter(new MainViewPageAdapter(getChildFragmentManager(), createFragments()));
    }


    private List<Fragment> createFragments() {
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(ProfileFragment.newInstance());
        fragments.add(RateItemsFragment.newInstance());
        fragments.add(BasketFragment.newInstance());
        return fragments;
    }

    private void initBottomNavigation(View view) {
        bottomNavigationView = view.findViewById(R.id.bnv_main_fr_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(
                item -> {
                    switch (item.getItemId()) {
                        case R.id.action_profile:
                            Timber.d("actionProfileClick()");
                            return true;
                        case R.id.action_likes:
                            Timber.d("actionProfileClick()");
                            return true;
                        case R.id.action_basket:
                            Timber.d("actionProfileClick()");
                            return true;
                    }
                    return false;
                });
    }
}
