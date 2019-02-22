package ru.fitsme.android.presentation.fragments.rateitem.view;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ru.fitsme.android.R;
import ru.fitsme.android.domain.entities.clothes.ClothesPage;

public class RateItemFragment extends Fragment {


    public RateItemFragment() {
    }

    public static RateItemFragment newInstance(ClothesPage clothesPage) {
        return new RateItemFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_rate_item, container, false);
    }

}
