package ru.fitsme.android.app;

import android.content.Context;
import android.util.DisplayMetrics;

import androidx.core.content.ContextCompat;

public class ResourceManager {

    private static Context appContext = App.getInstance().getApplicationContext();

    public static int getColor(int color) {
        return ContextCompat.getColor(appContext, color);
    }

    public static DisplayMetrics getDisplayMetrics() {
        return appContext.getResources().getDisplayMetrics();
    }
}
