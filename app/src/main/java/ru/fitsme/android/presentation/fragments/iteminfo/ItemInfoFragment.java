package ru.fitsme.android.presentation.fragments.iteminfo;


import android.arch.lifecycle.ViewModelProviders;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import javax.inject.Inject;

import ru.fitsme.android.R;
import ru.fitsme.android.app.App;
import ru.fitsme.android.domain.interactors.clothes.IClothesInteractor;

public class ItemInfoFragment extends Fragment {

    private static final String INDEX_KEY = "indexKey";

    @Inject
    IClothesInteractor clothesInteractor;

    private TextView textViewIndex;

    private IOnSwipeListener onSwipeListener = null;
    private int index;

    public ItemInfoFragment() {
        App.getInstance().getDi().inject(this);
    }

    public static ItemInfoFragment newInstance(int index) {
        ItemInfoFragment fragment = new ItemInfoFragment();

        Bundle args = new Bundle();
        args.putInt(INDEX_KEY, index);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item_info, container, false);

        textViewIndex = view.findViewById(R.id.tv_index);

        view.setOnTouchListener(new OnSwipeTouchListener(getContext()) {
            @Override
            public void onSwipeRight() {
                onSwipe(IOnSwipeListener.AnimationType.RIGHT);
            }

            @Override
            public void onSwipeLeft() {
                onSwipe(IOnSwipeListener.AnimationType.LEFT);
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        index = getArguments().getInt(INDEX_KEY);

        ItemInfoViewModel viewModel = ViewModelProviders.of(this,
                new ItemInfoViewModel.Factory(clothesInteractor, index))
                .get(ItemInfoViewModel.class);

        viewModel.getItemLiveData()
                .observe(this, this::onItem);
    }

    private void onItem(ItemInfoState itemInfoState) {
        switch (itemInfoState.getState()) {
            case ERROR:
                textViewIndex.setText("error");
                break;
            case LOADING:
                textViewIndex.setText("loading");
                break;
            case OK:
                if (index % 2 == 0) {
                    getView().setBackgroundColor(Color.DKGRAY);
                } else {
                    getView().setBackgroundColor(Color.LTGRAY);
                }
                textViewIndex.setText(index + " index");
                onSwipeListener = ((IOnSwipeListener) getParentFragment());
                break;
        }
        //TODO: реализовать отображение
    }

    private void onSwipe(IOnSwipeListener.AnimationType animationType) {
        if (onSwipeListener != null) {
            onSwipeListener.onSwipe(animationType);
        }
    }
}
