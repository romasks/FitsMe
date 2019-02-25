package ru.fitsme.android.presentation.fragments.rateitems.view;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import javax.inject.Inject;

import ru.fitsme.android.R;
import ru.fitsme.android.app.App;
import ru.fitsme.android.domain.interactors.clothes.IClothesInteractor;
import ru.fitsme.android.presentation.fragments.iteminfo.IOnSwipeListener;
import ru.fitsme.android.presentation.fragments.iteminfo.ItemInfoFragment;

public class RateItemsFragment extends Fragment implements IOnSwipeListener {
    @Inject
    IClothesInteractor clothesInteractor;

    private RateItemsViewModel viewModel;

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
        View view = inflater.inflate(R.layout.fragment_rate_items, container, false);

        Button button = view.findViewById(R.id.btn_like);
        button.setOnClickListener(v -> likeItem(true));

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = ViewModelProviders.of(this,
                new RateItemsViewModel.Factory(clothesInteractor)).get(RateItemsViewModel.class);

        viewModel.getIndexLiveData()
                .observe(this, this::onIndex);
    }

    @Override
    public void onSwipe(AnimationType animationType) {
        viewModel.likeClothesItem(false, animationType);
    }

    private void onIndex(RateItemsState rateItemsState) {
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();

        switch (rateItemsState.getAnimationType()) {
            case RIGHT:
                transaction.setCustomAnimations(R.anim.clothes_item_enter, R.anim.clothes_item_exit_right);
                break;
            case LEFT:
                transaction.setCustomAnimations(R.anim.clothes_item_enter, R.anim.clothes_item_exit_left);
                break;
            case SIMPLE:
                transaction.setCustomAnimations(R.anim.clothes_item_enter, R.anim.clothes_item_exit_simple);
                break;
        }
        transaction.replace(R.id.container, ItemInfoFragment.newInstance(rateItemsState.getIndex()))
                .commit();
    }

    private void likeItem(boolean liked) {
        viewModel.likeClothesItem(liked, AnimationType.SIMPLE);
    }
}
