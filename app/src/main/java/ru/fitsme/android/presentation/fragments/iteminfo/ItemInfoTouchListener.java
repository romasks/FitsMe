package ru.fitsme.android.presentation.fragments.iteminfo;

import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;

public class ItemInfoTouchListener implements View.OnTouchListener {
    private Callback callback;

    private int windowWidth;
    private int windowHeight;
    private int screenHorizontalCenter;
    private int screenVerticalCenter;

    private int firstTouchX;
    private int firstTouchY;

    ItemInfoTouchListener(ItemInfoFragment fragment){
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
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:{
                firstTouchX = (int) event.getRawX();
                firstTouchY = (int) event.getRawY();
                break;
            }
            case MotionEvent.ACTION_UP:{
                int touchX = (int) event.getRawX();
                int touchY = (int) event.getRawY();

                int deltaX = touchX - firstTouchX;
                int deltaY = touchY - firstTouchY;

                int TOUCH_SENSITIVITY = 16;

                if (Math.abs(deltaX) < windowWidth / TOUCH_SENSITIVITY &&
                        Math.abs(deltaY) < windowHeight / TOUCH_SENSITIVITY){
                    if (touchX < screenHorizontalCenter){
                        callback.previousPicture();
                    } else {
                        callback.nextPicture();
                    }
                }
                v.performClick();
            }
        }
        return true;
    }


    interface Callback{
        void nextPicture();
        void previousPicture();
    }
}
