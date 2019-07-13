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
    protected abstract T getValues();

    @NonNull
    public T getData() {
        return getValues();
    }

    public void setData(@NonNull T data) {
        SharedPreferences.Editor editor = getSharedPreferences().edit();

        setValues(editor, data);

        editor.apply();
    }

    protected String getStringValue(String key){
        return getSharedPreferences().getString(key, null);
    }

    protected int getIntValue(String key) throws DataNotFoundException {
        isContain(key);

        return getSharedPreferences().getInt(key, 0);
    }

    private boolean isContain(String key) {
        return getSharedPreferences().contains(key);
    }

    protected int getIntegerValue(String key) throws DataNotFoundException {
        if (getSharedPreferences().contains(key)) {
            return getSharedPreferences().getInt(key, 0);
        }
        throw new DataNotFoundException("Can't find " + key);
    }

    private SharedPreferences getSharedPreferences() {
        return appContext.getSharedPreferences(prefName, Context.MODE_PRIVATE);
    }
}
