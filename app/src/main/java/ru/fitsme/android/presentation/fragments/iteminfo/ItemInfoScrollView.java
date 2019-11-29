package ru.fitsme.android.presentation.fragments.iteminfo;


import android.content.Context;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ScrollView;

import ru.fitsme.android.presentation.fragments.rateitems.RateItemTouchListener;

public class ItemInfoScrollView extends ScrollView {

    private boolean isDetailState;

    private SparseArray<OnTouchListener> listenerArray = new SparseArray<>();
    private static final int RATE_ITEMS_LISTENER_KEY = 0;
    private static final int ITEM_INFO_LISTENER_KEY = 1;

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
        if (!isDetailState) {
            for (int i = 0; i < listenerArray.size(); i++) {
                listenerArray.get(i).onTouch(this, ev);
            }
            return true;
        } else {
            listenerArray.get(ITEM_INFO_LISTENER_KEY).onTouch(this, ev);
            return super.onTouchEvent(ev);
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {

        return super.onInterceptTouchEvent(ev);
    }

    void setDetailState(boolean detailState) {
        isDetailState = detailState;
    }

    public void setListener(View.OnTouchListener listener) {
        if (listener instanceof RateItemTouchListener){
            listenerArray.put(RATE_ITEMS_LISTENER_KEY, listener);
        } else if (listener instanceof ItemInfoTouchListener){
            listenerArray.put(ITEM_INFO_LISTENER_KEY, listener);
        }
    }

    public void clearListener(){
        listenerArray.clear();
    }
}
