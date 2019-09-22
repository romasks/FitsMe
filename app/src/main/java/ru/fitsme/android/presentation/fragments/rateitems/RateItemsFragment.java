package ru.fitsme.android.presentation.fragments.rateitems;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintSet;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.MotionEvent;
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
import ru.fitsme.android.presentation.main.view.MainActivity;
import timber.log.Timber;

public class RateItemsFragment extends BaseFragment<RateItemsViewModel>
        implements BindingEventsClickListener {

    private static final String KEY_ITEM_INFO_STATE = "state";

    @Inject
    IClothesInteractor clothesInteractor;

    private ItemInfoFragment currentFragment;
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

        viewModel = ViewModelProviders.of(this,
                new ViewModelFactory(clothesInteractor)).get(RateItemsViewModel.class);
        viewModel.init();

        viewModel.getRateItemsStateLiveData()
                .observe(this, this::onChange);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ((MainActivity) getActivity()).unsubscribe();
        viewModel.clearDisposables();
    }

    private void onChange(RateItemsState rateItemsState) {
        int containerWidth = binding.fragmentRateItemsContainer.getWidth();;
        int containerHeight;

        if (isFullItemInfoState){
            int px = binding.fragmentRateItemsButtonsGroup.getHeight();
            int bottomPx = ((MainFragment) getParentFragment()).getBottomNavigationSize();
            containerHeight = binding.fragmentRateItemsContainer.getHeight() - px - bottomPx;
        } else {
            containerHeight = binding.fragmentRateItemsContainer.getHeight();
        }

        currentFragment = ItemInfoFragment.newInstance(
                rateItemsState.getClotheInfo(), isFullItemInfoState, containerHeight, containerWidth);

        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_rate_items_container, currentFragment)
                .commit();
        resetContainerView();
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

    void maybeLikeItem(float alpha){
        currentFragment.showYes(true, alpha);
    }

    void likeItem() {
        currentFragment.showYes(true);
        ObjectAnimator animator =
                ObjectAnimator.ofFloat(binding.fragmentRateItemsContainer,
                        "translationX", 1500f);
        animator.setDuration(500);
        animator.start();
        animator.addListener(new Animator.AnimatorListener(){
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if (currentFragment != null) {
                    viewModel.likeClothesItem(true, IOnSwipeListener.AnimationType.RIGHT);
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }

    void maybeDislikeItem(float alpha){
        currentFragment.showNo(true, alpha);
    }

    void dislikeItem() {
        currentFragment.showNo(true);
        ObjectAnimator animator =
                ObjectAnimator.ofFloat(binding.fragmentRateItemsContainer,
                        "translationX", -1500f);
        animator.setDuration(500);
        animator.start();
        animator.addListener(new Animator.AnimatorListener(){
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if (currentFragment != null) {
                    viewModel.likeClothesItem(false, IOnSwipeListener.AnimationType.LEFT);
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }

    void resetContainerView(){
        binding.fragmentRateItemsContainer.animate()
                .rotation(0)
                .translationX(0)
                .translationY(0)
                .setDuration(300)
                .start();

//        ObjectAnimator animator =
//                ObjectAnimator.ofFloat(binding.fragmentRateItemsContainer,
//                        "translationX", 0f);
//        animator.setDuration(500);
//        animator.start();

//        binding.fragmentRateItemsContainer.setX(0);
//        binding.fragmentRateItemsContainer.setY(0);
//        binding.fragmentRateItemsContainer.setRotation(0);
    }

    public void setFullItemInfoState(boolean b) {
        isFullItemInfoState = b;
        getArguments().putBoolean(KEY_ITEM_INFO_STATE, isFullItemInfoState);
        if (b){
            binding.fragmentRateItemsReturnBtn.setVisibility(View.INVISIBLE);
            binding.fragmentRateItemsFilterBtn.setVisibility(View.INVISIBLE);
            ((MainFragment) getParentFragment()).showBottomNavigation(false);
            setConstraintToFullState(true);
        } else {
            binding.fragmentRateItemsReturnBtn.setVisibility(View.VISIBLE);
            binding.fragmentRateItemsFilterBtn.setVisibility(View.VISIBLE);
            ((MainFragment) getParentFragment()).showBottomNavigation(true);
            setConstraintToFullState(false);
        }
    }

    private void setConstraintToFullState(boolean b){
        ConstraintSet set = new ConstraintSet();
        set.clone(binding.rateItemsLayout);
        if (b) {
            set.connect(R.id.fragment_rate_items_container, ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM);
            binding.fragmentRateItemsContainer.setOnTouchListener(null);
            ((MainActivity) getActivity()).unsubscribe();
        } else {
            set.connect(R.id.fragment_rate_items_container, ConstraintSet.BOTTOM, R.id.fragment_rate_items_buttons_group, ConstraintSet.TOP);
            setOnTouchListener();
        }
        set.applyTo(binding.rateItemsLayout);
    }

    private void setOnTouchListener() {
        RateItemTouchListener rateItemTouchListener = new RateItemTouchListener(this, binding);
        binding.fragmentRateItemsContainer.setOnTouchListener(rateItemTouchListener);
        ((MainActivity) getActivity()).observeTouch(rateItemTouchListener);
    }
}
