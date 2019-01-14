package ru.fitsme.android.app;

import android.app.Application;

import timber.log.Timber;

public class App extends Application {

    private static App app;

    private DI di;

    public App() {
        app = this;
    }

    public static App getApp() {
        return app;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        Timber.plant(new Timber.DebugTree());

        di = new DI();
    }

    public DI getDi() {
        return di;
    }
}
