package ru.fitsme.android.presentation.fragments.rateitems;

import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;

import ru.fitsme.android.databinding.FragmentRateItemsBinding;

public class RateItemTouchListener implements View.OnTouchListener {
    private RateItemsFragment fragment;
    private FragmentRateItemsBinding binding;

    private Rating liked = Rating.RESET;

    private int windowWidth;
    private int windowHeight;
    private int screenHorizontalCenter;
    private int screenVerticalCenter;

    private int touchX;
    private int touchY;
    private int firstTouchX;
    private int firstTouchY;
    private int deltaX;
    private int deltaY;

    RateItemTouchListener(RateItemsFragment fragment, FragmentRateItemsBinding binding){
        this.fragment = fragment;
        this.binding = binding;
        DisplayMetrics displayMetrics = new DisplayMetrics();
        fragment.getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        windowWidth = displayMetrics.widthPixels;
        windowHeight = displayMetrics.heightPixels;
        screenHorizontalCenter = windowWidth / 2;
        screenVerticalCenter = windowHeight / 2;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        touchX = (int) event.getRawX();
        touchY = (int) event.getRawY();

        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                firstTouchX = (int) event.getRawX();
                firstTouchY = (int) event.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                touchX = (int) event.getRawX();
                touchY = (int) event.getRawY();

                deltaX = touchX - firstTouchX;
                deltaY = touchY - firstTouchY;

                binding.fragmentRateItemsContainer.setX(deltaX);
                binding.fragmentRateItemsContainer.setY(deltaY);

                if (deltaX >= 0) {
                    binding.fragmentRateItemsContainer.setRotation((float) (deltaX * (Math.PI / 64)));
                    fragment.maybeLikeItem(4 * (float) Math.abs(deltaX) / windowWidth);
                    if (Math.abs(deltaX) > windowWidth / 4){
                        fragment.maybeLikeItem(1.0f);
                        liked = Rating.LIKED;
                    }
                    else {
                        liked = Rating.RESET;
                    }
                } else {
                    binding.fragmentRateItemsContainer.setRotation((float) (deltaX * (Math.PI / 64)));
                    fragment.maybeDislikeItem(4 * (float) Math.abs(deltaX) / windowWidth);
                    if (Math.abs(deltaX) > windowWidth / 4){
                        fragment.maybeDislikeItem(1.0f);
                        liked = Rating.DISLIKED;
                    } else {
                        liked = Rating.RESET;
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                if (liked == Rating.RESET){
                    fragment.maybeLikeItem(0);
                    fragment.resetContainerView();
                } else if (liked == Rating.DISLIKED){
                    fragment.dislikeItem();
                } else if (liked == Rating.LIKED){
                    fragment.likeItem();
                }
                break;
            default:
                break;
        }
        return true;
    }

    enum Rating{
        RESET, DISLIKED, LIKED
    }
}
