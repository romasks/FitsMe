package ru.fitsme.android.data.frameworks.sharedpreferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import javax.inject.Inject;

import ru.fitsme.android.domain.entities.clothes.LastItem;
import ru.fitsme.android.domain.entities.exceptions.internal.DataNotFoundException;

public class LastPageStorage extends SharedPreferencesStorage<LastItem> {

    private static final String PREF_NAME = "lastPagePref";
    private static final String PAGE_KEY = "pageKey";
    private static final String INDEX_KEY = "indexKey";

    @Inject
    LastPageStorage(Context appContext) {
        super(appContext, PREF_NAME);
    }

    @Override
    protected void setValues(@NonNull SharedPreferences.Editor editor, @NonNull LastItem data) {
        editor.putInt(PAGE_KEY, data.getPage());
        editor.putInt(INDEX_KEY, data.getIndex());
    }

    @NonNull
    @Override
    protected LastItem getValues() throws DataNotFoundException {
        int page = getIntValue(PAGE_KEY);
        int index = getIntValue(INDEX_KEY);
        return new LastItem(page, index);
    }
}
