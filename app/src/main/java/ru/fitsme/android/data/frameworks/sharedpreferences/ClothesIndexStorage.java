package ru.fitsme.android.data.frameworks.sharedpreferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import javax.inject.Inject;

import ru.fitsme.android.domain.entities.exceptions.internal.DataNotFoundException;

public class ClothesIndexStorage extends SharedPreferencesStorage<Integer> {

    private static final String PREF_NAME = "clothesItemIndexPref";

    private static final String INDEX_KEY = "indexKey";

    @Inject
    ClothesIndexStorage(Context appContext) {
        super(appContext, PREF_NAME);
    }

    @Override
    protected void setValues(@NonNull SharedPreferences.Editor editor, @NonNull Integer data) {
        editor.putInt(INDEX_KEY, data);
    }

    @NonNull
    @Override
    protected Integer getValues() {
        int i = 0;
        try {
            i = getIntegerValue(INDEX_KEY);
        } catch (DataNotFoundException e) {
            e.printStackTrace();
        }
        return i;
    }
}
