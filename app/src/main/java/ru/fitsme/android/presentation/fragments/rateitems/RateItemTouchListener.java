package ru.fitsme.android.presentation.fragments.rateitems;

import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;

public class RateItemTouchListener implements View.OnTouchListener {
    private Callback callback;

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

    RateItemTouchListener(RateItemsFragment fragment){
        this.callback = (Callback) fragment;
        DisplayMetrics displayMetrics = new DisplayMetrics();
        fragment.getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        windowWidth = displayMetrics.widthPixels;
        windowHeight = displayMetrics.heightPixels;
        screenHorizontalCenter = windowWidth / 2;
        screenVerticalCenter = windowHeight / 2;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
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

                callback.moveViewToXY(deltaX, deltaY);
                callback.rotateView((float) (deltaX * (Math.PI / 64)));

                if (deltaX >= 0) {
                    callback.maybeLikeItem(4 * (float) Math.abs(deltaX) / windowWidth);
                    if (Math.abs(deltaX) > windowWidth / 4){
                        callback.maybeLikeItem(1.0f);
                        liked = Rating.LIKED;
                    }
                    else {
                        liked = Rating.RESET;
                    }
                } else {
                    callback.maybeDislikeItem(4 * (float) Math.abs(deltaX) / windowWidth);
                    if (Math.abs(deltaX) > windowWidth / 4){
                        callback.maybeDislikeItem(1.0f);
                        liked = Rating.DISLIKED;
                    } else {
                        liked = Rating.RESET;
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                if (liked == Rating.RESET){
                    callback.maybeLikeItem(0);
                    callback.resetContainerViewWithAnimation();
                } else if (liked == Rating.DISLIKED){
                    callback.startToDislikeItem();
                } else if (liked == Rating.LIKED){
                    callback.startToLikeItem();
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

    interface Callback {

        void moveViewToXY(int deltaX, int deltaY);

        void rotateView(float degrees);

        void maybeLikeItem(float alpha);

        void maybeDislikeItem(float alpha);

        void startToLikeItem();

        void startToDislikeItem();

        void resetContainerViewWithAnimation();

        void resetContainerView();
    }
}
