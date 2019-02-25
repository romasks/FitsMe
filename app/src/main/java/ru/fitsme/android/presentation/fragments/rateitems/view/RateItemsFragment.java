package ru.fitsme.android.presentation.fragments.rateitems.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import ru.fitsme.android.R;
import ru.fitsme.android.presentation.fragments.rateitems.adapter.ClothesPageAdapter;

public class RateItemsFragment extends Fragment {
    private ViewPager clothesViewPager;

    public RateItemsFragment() {
    }

    public static RateItemsFragment newInstance() {
        RateItemsFragment fragment = new RateItemsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_rate_items, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        clothesViewPager = view.findViewById(R.id.vp_fr_rate_items_frame);
        clothesViewPager.setAdapter(new ClothesPageAdapter(getChildFragmentManager(), new ArrayList<>()));
    }
}
