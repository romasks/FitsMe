package ru.fitsme.android.app;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import timber.log.Timber;

public class AppStatus {

    private static AppStatus instance;
    private ConnectivityManager connectivityManager;
    private boolean connected = false;

    public AppStatus() {
        instance = this;
    }

    public static AppStatus getInstance() {
        return instance;
    }

    public boolean isOnline() {
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) App.getInstance().getApplicationContext()
                    .getSystemService(Context.CONNECTIVITY_SERVICE);

            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            connected = networkInfo != null && networkInfo.isAvailable() &&
                    networkInfo.isConnected();
            return connected;
        } catch (Exception e) {
            Timber.e("connectivity: %s", e.toString());
        }
        return connected;
    }
}
