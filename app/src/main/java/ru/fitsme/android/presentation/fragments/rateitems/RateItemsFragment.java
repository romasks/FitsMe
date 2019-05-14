package ru.fitsme.android.presentation.fragments.rateitems;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import javax.inject.Inject;

import ru.fitsme.android.R;
import ru.fitsme.android.app.App;
import ru.fitsme.android.databinding.FragmentRateItemsBinding;
import ru.fitsme.android.domain.interactors.clothes.IClothesInteractor;
import ru.fitsme.android.presentation.fragments.iteminfo.ItemInfoFragment;

public class RateItemsFragment extends Fragment
        implements IOnSwipeListener, BindingEventsClickListener {
    @Inject
    IClothesInteractor clothesInteractor;

    private RateItemsViewModel viewModel;
    private ItemInfoFragment curFragment;
    private FragmentRateItemsBinding binding;

    public RateItemsFragment() {
        App.getInstance().getDi().inject(this);
    }

    public static RateItemsFragment newInstance() {
        return new RateItemsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_rate_items, container, false);
        binding.setBindingEvents(this);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

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

        viewModel = ViewModelProviders.of(this,
                new RateItemsViewModel.Factory(clothesInteractor)).get(RateItemsViewModel.class);
        if (savedInstanceState == null) {
            viewModel.init();
        }

        viewModel.getIndexLiveData()
                .observe(this, this::onIndex);
    }

    @Override
    public void onSwipe(AnimationType animationType) {
        if (curFragment != null && curFragment.isActive()) {
            viewModel.likeClothesItem(false, animationType);
        }
    }

    private void onIndex(RateItemsState rateItemsState) {
        curFragment = ItemInfoFragment.newInstance(rateItemsState.getIndex());

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
        transaction.replace(R.id.container, curFragment)
                .commit();
    }

    @Override
    public void onClickLikeItem() {
        likeItem();
    }

    private void likeItem() {
        if (curFragment != null && curFragment.isActive()) {
            viewModel.likeClothesItem(true, AnimationType.SIMPLE);
        }
    }
}
