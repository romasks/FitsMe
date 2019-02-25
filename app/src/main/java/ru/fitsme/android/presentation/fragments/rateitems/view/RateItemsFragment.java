package ru.fitsme.android.presentation.fragments.rateitems.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import javax.inject.Inject;

import ru.fitsme.android.R;
import ru.fitsme.android.app.App;
import ru.fitsme.android.domain.interactors.clothes.IClothesInteractor;

public class RateItemsFragment extends Fragment {
    @Inject
    IClothesInteractor clothesInteractor;

    public RateItemsFragment() {
        App.getInstance().getDi().inject(this);
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


    }
}
