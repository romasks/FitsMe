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
        String value = getSharedPreferences().getString(key, null);
        if (value == null) {
            throw new DataNotFoundException();
        }
        return value;
    }

    private SharedPreferences getSharedPreferences() {
        return appContext.getSharedPreferences(prefName, Context.MODE_PRIVATE);
    }
}
