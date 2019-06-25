package ru.fitsme.android.presentation.fragments.rateitems;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import javax.inject.Inject;

import ru.fitsme.android.R;
import ru.fitsme.android.databinding.FragmentRateItemsBinding;
import ru.fitsme.android.domain.interactors.clothes.IClothesInteractor;
import ru.fitsme.android.presentation.fragments.base.BaseFragment;
import ru.fitsme.android.presentation.fragments.base.ViewModelFactory;
import ru.fitsme.android.presentation.fragments.iteminfo.ItemInfoFragment;
import ru.fitsme.android.presentation.fragments.main.MainFragment;
import timber.log.Timber;

public class RateItemsFragment extends BaseFragment<RateItemsViewModel>
        implements IOnSwipeListener, BindingEventsClickListener {

    private static final String KEY_ITEM_INFO_STATE = "state";

    @Inject
    IClothesInteractor clothesInteractor;

    private ItemInfoFragment curFragment;
    private FragmentRateItemsBinding binding;
    private boolean isFullItemInfoState;

    public static RateItemsFragment newInstance() {
        return new RateItemsFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null){
            isFullItemInfoState = getArguments().getBoolean(KEY_ITEM_INFO_STATE);
        } else {
            isFullItemInfoState = false;
            Bundle bundle = new Bundle();
            bundle.putBoolean(KEY_ITEM_INFO_STATE, isFullItemInfoState);
            setArguments(bundle);
        }
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
                likeItem();
            }

            @Override
            public void onSwipeLeft() {
                dislikeItem();
            }
        });

        viewModel = ViewModelProviders.of(this,
                new ViewModelFactory(clothesInteractor)).get(RateItemsViewModel.class);
        if (savedInstanceState == null) {
            viewModel.init();
        }

        viewModel.getIndexLiveData()
                .observe(this, this::onIndex);
    }

    @Override
    public void onSwipe(AnimationType animationType) {
    }

    private void onIndex(RateItemsState rateItemsState) {
        curFragment = ItemInfoFragment.newInstance(rateItemsState.getIndex(), isFullItemInfoState);

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
        transaction.replace(R.id.fragment_rate_items_container, curFragment)
                .commit();
    }

    @Override
    public void onClickLikeItem() {
        likeItem();
    }

    @Override
    public void onClickRefresh() {
        Timber.d("onClickRefresh()");
    }

    @Override
    public void onClickDislikeItem() {
        dislikeItem();
    }

    @Override
    public void onClickFilter() {
        Timber.d("onClickFilter()");
    }

    private void likeItem() {
        curFragment.showYes(true);
        if (curFragment != null && curFragment.isActive()) {
            viewModel.likeClothesItem(true, IOnSwipeListener.AnimationType.RIGHT);
        }
    }

    private void dislikeItem() {
        curFragment.showNo(true);
        if (curFragment != null && curFragment.isActive()) {
            viewModel.likeClothesItem(false, IOnSwipeListener.AnimationType.LEFT);
        }
    }

    public void setFullItemInfoState(boolean b) {
        isFullItemInfoState = b;
        getArguments().putBoolean(KEY_ITEM_INFO_STATE, isFullItemInfoState);
        if (b){
            binding.fragmentRateItemsReturnBtn.setVisibility(View.INVISIBLE);
            binding.fragmentRateItemsFilterBtn.setVisibility(View.INVISIBLE);
            ((MainFragment) getParentFragment()).showBottomNavigation(false);
        } else {
            binding.fragmentRateItemsReturnBtn.setVisibility(View.VISIBLE);
            binding.fragmentRateItemsFilterBtn.setVisibility(View.VISIBLE);
            ((MainFragment) getParentFragment()).showBottomNavigation(true);
        }
    }
}
