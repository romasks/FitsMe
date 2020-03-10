package ru.fitsme.android.app;

import androidx.multidex.MultiDexApplication;

import com.crashlytics.android.Crashlytics;

import io.fabric.sdk.android.Fabric;
import ru.fitsme.android.app.di.DI;
import ru.fitsme.android.data.frameworks.room.db.AppDatabase;
import ru.fitsme.android.domain.entities.auth.AuthInfo;
import timber.log.Timber;

public class App extends MultiDexApplication {

    private static App instance;
    private DI di;
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
        AppDatabase.create(this);
        Fabric.with(this, new Crashlytics());

        Timber.plant(new Timber.DebugTree());

        di = new DI(this);
    }

    public DI getDi() {
        return di;
    }

    public AuthInfo getAuthInfo() {
        return authInfo;
    }

    public void setAuthInfo(AuthInfo authInfo) {
        this.authInfo = authInfo;
    }
}
