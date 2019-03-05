package ru.fitsme.android.presentation.fragments.main.view;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ru.fitsme.android.R;
import ru.fitsme.android.presentation.fragments.basket.BasketFragment;
import ru.fitsme.android.presentation.fragments.favourites.view.FavouritesFragment;
import ru.fitsme.android.presentation.fragments.profile.view.ProfileFragment;
import ru.fitsme.android.presentation.fragments.rateitems.view.RateItemsFragment;


public class MainFragment extends Fragment {
    private BottomNavigationView bottomNavigationView;

    public MainFragment() {
    }

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initBottomNavigation(view);
    }

    private void initBottomNavigation(View view) {
        bottomNavigationView = view.findViewById(R.id.bnv_main_fr_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(
                item -> {
                    switch (item.getItemId()) {
                        /*case R.id.action_profile:
                            switchFragment(ProfileFragment.newInstance());
                            return true;*/
                        case R.id.action_favourites:
                            switchFragment(FavouritesFragment.newInstance());
                            return true;
                        case R.id.action_likes:
                            switchFragment(RateItemsFragment.newInstance());
                            return true;
                        case R.id.action_basket:
                            switchFragment(BasketFragment.newInstance());
                            return true;
                    }
                    return false;
                });
        bottomNavigationView.setSelectedItemId(R.id.action_likes);
    }

    private void switchFragment(Fragment fragment) {
        getChildFragmentManager().beginTransaction()
                .replace(R.id.container, fragment)
                .commit();
    }
}
