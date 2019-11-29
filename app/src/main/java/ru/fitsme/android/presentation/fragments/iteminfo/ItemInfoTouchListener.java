package ru.fitsme.android.presentation.fragments.iteminfo;

import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;

public class ItemInfoTouchListener implements View.OnTouchListener  {

    private static final long MAX_CLICK_DURATION = 250;

    private ItemInfoTouchListener.Callback callback;

    private int windowWidth;
    private int windowHeight;
    private int screenHorizontalCenter;
    private int screenVerticalCenter;

    private int downEventX;
    private int downEvenY;
    private long downEventTime;

    public ItemInfoTouchListener(ItemInfoFragment fragment){
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
            case MotionEvent.ACTION_UP:
                handleClickEvent();
                v.performClick();
                break;
            default:
                break;
        }
        return true;
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

    private void handleDownEvent(MotionEvent event) {
        downEventTime = System.currentTimeMillis();
        downEventX = (int) event.getRawX();
        downEvenY = (int) event.getRawY();
    }



    interface Callback {
        void previousPicture();
        void nextPicture();
    }
}
