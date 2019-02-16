package ru.fitsme.android.presentation.fragments.rateitems.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ru.fitsme.android.R;

public class RateItemsFragment extends Fragment {

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

}
