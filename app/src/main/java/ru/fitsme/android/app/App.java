package ru.fitsme.android.app;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.crashlytics.FirebaseCrashlytics;
import com.jakewharton.threetenabp.AndroidThreeTen;

import java.util.UUID;

import androidx.multidex.MultiDexApplication;
import ru.fitsme.android.app.di.DI;
import ru.fitsme.android.data.frameworks.room.db.AppDatabase;
import ru.fitsme.android.domain.entities.auth.AuthInfo;
import timber.log.Timber;

public class App extends MultiDexApplication {

    private static App instance;
    private DI di;
    private AuthInfo authInfo;

    private static String uniqueID = null;
    private static final String PREF_UNIQUE_ID = "PREF_UNIQUE_ID";

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

        FirebaseAnalytics.getInstance(this);
        FirebaseCrashlytics.getInstance().setCrashlyticsCollectionEnabled(true);

        Timber.plant(new Timber.DebugTree());
        AndroidThreeTen.init(this);

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

    public String getDeviceId() {
        if (uniqueID == null) {
            SharedPreferences sharedPrefs = getApplicationContext().getSharedPreferences(PREF_UNIQUE_ID, Context.MODE_PRIVATE);
            uniqueID = sharedPrefs.getString(PREF_UNIQUE_ID, null);
            if (uniqueID == null) {
                uniqueID = UUID.randomUUID().toString();
                SharedPreferences.Editor editor = sharedPrefs.edit();
                editor.putString(PREF_UNIQUE_ID, uniqueID);
                editor.commit();
            }
        }
        return uniqueID;
    }
}
