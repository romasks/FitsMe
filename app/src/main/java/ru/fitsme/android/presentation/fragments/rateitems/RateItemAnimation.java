package ru.fitsme.android.presentation.fragments.rateitems;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.util.DisplayMetrics;

import ru.fitsme.android.databinding.FragmentRateItemsBinding;

class RateItemAnimation {

    private final int screenWidth;
    private Callback callback;
    private FragmentRateItemsBinding binding;

    private static final int OUT_OF_SCREEN_DURATION = 300;


    RateItemAnimation(RateItemsFragment fragment, FragmentRateItemsBinding binding) {
        this.callback = fragment;
        this.binding = binding;
        DisplayMetrics displayMetrics = new DisplayMetrics();
        if (fragment.getActivity() != null) {
            fragment.getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        }
        screenWidth = displayMetrics.widthPixels;
    }

    void resetContainerViewWithAnimation() {
        binding.fragmentRateItemsContainer.animate()
                .rotation(0)
                .translationX(0)
                .translationY(0)
                .setDuration(300)
                .start();
    }

    void resetContainerView() {
        binding.fragmentRateItemsContainer.setX(0);
        binding.fragmentRateItemsContainer.setY(0);
        binding.fragmentRateItemsContainer.setRotation(0);
    }

    void moveViewOutOfScreenToRight() {
        float moveDistance = (float) screenWidth / 2 + binding.fragmentRateItemsContainer.getHeight();
        ObjectAnimator animator =
                ObjectAnimator.ofFloat(binding.fragmentRateItemsContainer,
                        "translationX", moveDistance);
        animator.setDuration(OUT_OF_SCREEN_DURATION);
        animator.start();
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                callback.likeItem();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }

    void moveViewOutOfScreenToLeft() {
        float moveDistance = -((float) screenWidth / 2 + binding.fragmentRateItemsContainer.getHeight());
        ObjectAnimator animator =
                ObjectAnimator.ofFloat(binding.fragmentRateItemsContainer,
                        "translationX", moveDistance);
        animator.setDuration(OUT_OF_SCREEN_DURATION);
        animator.start();
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                callback.dislikeItem();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }

    void moveViewToXY(int deltaX, int deltaY) {
        binding.fragmentRateItemsContainer.setX(deltaX);
        binding.fragmentRateItemsContainer.setY(deltaY);
    }

    void rotateView(float degrees) {
        binding.fragmentRateItemsContainer.setRotation(degrees);
    }

    interface Callback {
        void likeItem();

        void dislikeItem();
    }
}
