package ru.fitsme.android.app;

import android.app.Application;

import timber.log.Timber;

public class App extends Application {

    private static App instance;
    private DI di;

    public App() {
        instance = this;
    }

    public static App getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        Timber.plant(new Timber.DebugTree());

        di = new DI(this);
    }

    public DI getDi() {
        return di;
    }
}
