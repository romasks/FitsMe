package ru.fitsme.android.data.frameworks.sharedpreferences;

import android.content.Context;
import android.content.SharedPreferences;

import javax.inject.Inject;

import ru.fitsme.android.domain.entities.auth.AuthInfo;

public class AuthInfoStorage implements IAuthInfoStorage {

    private static final String PREF_NAME = "authInfoPref";
    private static final String LOGIN_KEY = "loginKey";
    private static final String TOKEN_KEY = "tokenKey";

    private SharedPreferences sharedPreferences;

    @Inject
    AuthInfoStorage(Context appContext) {
        sharedPreferences = appContext.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    @Override
    public AuthInfo getAuthInfo() {
        String login = sharedPreferences.getString(LOGIN_KEY, null);
        String token = sharedPreferences.getString(TOKEN_KEY, null);
        return new AuthInfo(login, token);
    }

    @Override
    public void setAuthInfo(AuthInfo authInfo){
        sharedPreferences.edit()
                .putString(LOGIN_KEY, authInfo.getLogin())
                .putString(TOKEN_KEY, authInfo.getToken())
                .apply();
    }
}
