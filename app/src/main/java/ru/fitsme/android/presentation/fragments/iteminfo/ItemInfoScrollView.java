package ru.fitsme.android.presentation.fragments.iteminfo;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ScrollView;

public class ItemInfoScrollView extends ScrollView {

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
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:{
                break;
            }
            case MotionEvent.ACTION_UP:{
                performClick();
                break;
            }
        }
        return false;
    }

    @Override
    public boolean performClick() {
        return super.performClick();
    }
}
