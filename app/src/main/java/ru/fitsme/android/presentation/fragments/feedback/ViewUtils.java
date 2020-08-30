package ru.fitsme.android.presentation.fragments.feedback;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;

class ViewUtils {

    static void scrollUp(View view) {
        ScrollView scrollView = (ScrollView) view.getParent().getParent();
        LinearLayout scrollLayout = (LinearLayout) view.getParent();
        int delta = 0;
        for (int i = 0; i < scrollLayout.getChildCount(); i++) {
            if (view == scrollLayout.getChildAt(i)) break;
            delta += scrollLayout.getChildAt(i).getHeight();
        }
        scrollView.smoothScrollTo(0, delta);
    }
}
