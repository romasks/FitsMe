package ru.fitsme.android.presentation.fragments.rateitems;

import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;

public class RateItemTouchListener implements View.OnTouchListener {
    private Callback callback;

    private static final long MAX_CLICK_DURATION = 250;

    private Rating liked = Rating.RESET;

    private int windowWidth;
    private int windowHeight;
    private int screenHorizontalCenter;
    private int screenVerticalCenter;

    private int moveEventX;
    private int moveEventY;
    private int downEventX;
    private int downEvenY;
    private long downEventTime;
    private int deltaX;
    private int deltaY;

    RateItemTouchListener(RateItemsFragment fragment) {
        this.callback = fragment;
        DisplayMetrics displayMetrics = new DisplayMetrics();
        if (fragment.getActivity() != null) {
            fragment.getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        }
        windowWidth = displayMetrics.widthPixels;
        windowHeight = displayMetrics.heightPixels;
        screenHorizontalCenter = windowWidth / 2;
        screenVerticalCenter = windowHeight / 2;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                handleDownEvent(event);
                break;
            case MotionEvent.ACTION_MOVE:
                handleMoveEvent(event);
                handleMoveCallbacks();
                break;
            case MotionEvent.ACTION_UP:
                handleSwipeEvent();
                handleClickEvent();
                v.performClick();
                break;
            default:
                break;
        }
        return true;
    }

    private void handleDownEvent(MotionEvent event) {
        downEventTime = System.currentTimeMillis();
        downEventX = (int) event.getRawX();
        downEvenY = (int) event.getRawY();
    }

    private void handleClickEvent() {
        long upEventTime = System.currentTimeMillis();
        if (upEventTime - downEventTime < MAX_CLICK_DURATION){
            if (downEventX < screenHorizontalCenter){
                callback.previousPicture();
            } else {
                callback.nextPicture();
            }
        }
    }

    private void handleSwipeEvent() {
        if (liked == Rating.RESET) {
            callback.maybeLikeItem(0);
            callback.resetContainerViewWithAnimation();
        } else if (liked == Rating.DISLIKED) {
            callback.startToDislikeItem();
        } else if (liked == Rating.LIKED) {
            callback.startToLikeItem();
        }
    }

    private void handleMoveCallbacks() {
        callback.moveViewToXY(deltaX, deltaY);
        callback.rotateView((float) (deltaX * (Math.PI / 64)));

        if (deltaX >= 0) {
            if (Math.abs(deltaX) > windowWidth / 4) {
                callback.maybeLikeItem(1.0f);
                liked = Rating.LIKED;
            } else {
                callback.maybeLikeItem(4 * (float) Math.abs(deltaX) / windowWidth);
                liked = Rating.RESET;
            }
        } else {
            if (Math.abs(deltaX) > windowWidth / 4) {
                callback.maybeDislikeItem(1.0f);
                liked = Rating.DISLIKED;
            } else {
                callback.maybeDislikeItem(4 * (float) Math.abs(deltaX) / windowWidth);
                liked = Rating.RESET;
            }
        }
    }

    private void handleMoveEvent(MotionEvent event) {
        moveEventX = (int) event.getRawX();
        moveEventY = (int) event.getRawY();

        deltaX = moveEventX - downEventX;
        deltaY = moveEventY - downEvenY;
    }

    enum Rating{
        RESET, DISLIKED, LIKED
    }

    interface Callback {

        void moveViewToXY(int deltaX, int deltaY);

        void rotateView(float degrees);

        void maybeLikeItem(float alpha);

        void maybeDislikeItem(float alpha);

        void startToLikeItem();

        void startToDislikeItem();

        void resetContainerViewWithAnimation();

        void resetContainerView();

        void previousPicture();

        void nextPicture();
    }
}
