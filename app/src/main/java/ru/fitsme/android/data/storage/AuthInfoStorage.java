package ru.fitsme.android.data.storage;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import javax.inject.Inject;

import ru.fitsme.android.data.entities.exceptions.internal.DataNotFoundException;
import ru.fitsme.android.data.entities.response.signinup.AuthInfo;

public class AuthInfoStorage extends SharedPreferencesStorage<AuthInfo> {

    private static final String PREF_NAME = "authInfoPref";
    private static final String LOGIN_KEY = "loginKey";
    private static final String TOKEN_KEY = "tokenKey";

    @Inject
    AuthInfoStorage(Context appContext) {
        super(appContext, PREF_NAME);
    }

    @Override
    protected void setValues(@NonNull SharedPreferences.Editor editor, @NonNull AuthInfo data) {
        editor.putString(LOGIN_KEY, data.getLogin());
        editor.putString(TOKEN_KEY, data.getToken());
    }

    @Override
    @NonNull
    protected AuthInfo getValues() throws DataNotFoundException {
        String login = getStringValue(LOGIN_KEY);
        String token = getStringValue(TOKEN_KEY);
        return new AuthInfo(login, token);
    }
}
