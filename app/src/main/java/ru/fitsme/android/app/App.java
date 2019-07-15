package ru.fitsme.android.app;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.crashlytics.android.Crashlytics;
import io.fabric.sdk.android.Fabric;
import ru.fitsme.android.app.di.DI;
import ru.fitsme.android.domain.entities.auth.AuthInfo;
import timber.log.Timber;

public class App extends Application {

    private static App instance;
    private DI di;
    private boolean connected = false;
    private AuthInfo authInfo;

    public App() {
        instance = this;
    }

    public static App getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Crashlytics());

        Timber.plant(new Timber.DebugTree());

        di = new DI(this);
    }

    public DI getDi() {
        return di;
    }

    public boolean isOnline() {
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) getApplicationContext()
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

    public AuthInfo getAuthInfo() {
        return authInfo;
    }

    public void setAuthInfo(AuthInfo authInfo) {
        this.authInfo = authInfo;
    }
}
