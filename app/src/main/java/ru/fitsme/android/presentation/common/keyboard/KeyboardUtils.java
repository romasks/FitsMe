package ru.fitsme.android.presentation.common.keyboard;

import android.app.Activity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.Nullable;

public class KeyboardUtils {
    public static void hide(@Nullable Activity activity, @Nullable View view) {
        if (activity != null && view != null) {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.hideSoftInputFromWindow(view.getRootView().getWindowToken(), 0);
            }
        }
    }
}
