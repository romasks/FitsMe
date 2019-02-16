package ru.fitsme.android.data.frameworks.sharedpreferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import ru.fitsme.android.domain.entities.exceptions.internal.DataNotFoundException;

public abstract class SharedPreferencesStorage<T> {

    private final Context appContext;
    private final String prefName;

    public SharedPreferencesStorage(Context appContext, String prefName) {
        this.appContext = appContext;
        this.prefName = prefName;
    }

    protected abstract void setValues(@NonNull SharedPreferences.Editor editor, @NonNull T data);

    @NonNull
    protected abstract T getValues() throws DataNotFoundException;

    @NonNull
    public T getData() throws DataNotFoundException {
        return getValues();
    }

    public void setData(@NonNull T data) {
        SharedPreferences.Editor editor = getSharedPreferences().edit();

        setValues(editor, data);

        editor.apply();
    }

    protected String getStringValue(String key) throws DataNotFoundException {
        checkContains(key);

        return getSharedPreferences().getString(key, null);
    }

    protected int getIntValue(String key) throws DataNotFoundException {
        checkContains(key);

        return getSharedPreferences().getInt(key, 0);
    }

    private void checkContains(String key) throws DataNotFoundException {
        if (getSharedPreferences().contains(key)) {
            throw new DataNotFoundException();
        }
    }

    private SharedPreferences getSharedPreferences() {
        return appContext.getSharedPreferences(prefName, Context.MODE_PRIVATE);
    }
}
