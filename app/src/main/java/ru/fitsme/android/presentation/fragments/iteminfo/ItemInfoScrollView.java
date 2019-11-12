package ru.fitsme.android.presentation.fragments.iteminfo;


import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ScrollView;

import ru.fitsme.android.presentation.fragments.rateitems.RateItemTouchListener;

public class ItemInfoScrollView extends ScrollView {

    private RateItemTouchListener listener;

    public ItemInfoScrollView(Context context) {
        super(context);
    }

    public ItemInfoScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ItemInfoScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public ItemInfoScrollView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (listener != null){
            listener.onTouch(this, ev);
        }
        return super.onTouchEvent(ev);
    }



    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {

        return super.onInterceptTouchEvent(ev);
    }

    public void setListener(RateItemTouchListener listener) {
        this.listener = listener;
    }

    public void clearListener(){
        this.listener = null;
    }
}
