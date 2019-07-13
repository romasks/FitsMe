package ru.fitsme.android.data.frameworks.sharedpreferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import javax.inject.Inject;

import ru.fitsme.android.domain.entities.auth.SignInInfo;
import ru.fitsme.android.domain.entities.exceptions.internal.DataNotFoundException;

public class SignInUpInfoStorage extends SharedPreferencesStorage<SignInInfo> {
    private static final String PREF_NAME = "signInUpInfoPref";
    private static final String LOGIN_KEY = "loginKey";
    private static final String PASSWORD_KEY = "passwordKey";

    @Inject
    public SignInUpInfoStorage(Context appContext) {
        super(appContext, PREF_NAME);
    }

    @Override
    protected void setValues(@NonNull SharedPreferences.Editor editor, @NonNull SignInInfo data) {
        editor.putString(LOGIN_KEY, data.getLogin());
        editor.putString(PASSWORD_KEY, data.getPasswordHash());
    }

    @Override
    @NonNull
    protected SignInInfo getValues() {
        String login = getStringValue(LOGIN_KEY);
        String passwordHash = getStringValue(PASSWORD_KEY);
        return SignInInfo.create(login, passwordHash);
    }
}
