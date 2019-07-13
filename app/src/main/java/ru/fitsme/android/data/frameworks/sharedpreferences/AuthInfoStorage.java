package ru.fitsme.android.data.frameworks.sharedpreferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import javax.inject.Inject;

import ru.fitsme.android.app.App;
import ru.fitsme.android.domain.entities.auth.AuthInfo;
import timber.log.Timber;

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

//    @Override
//    protected void setValues(@NonNull SharedPreferences.Editor editor, @NonNull AuthInfo data) {
//        editor.putString(LOGIN_KEY, data.getLogin());
//        editor.putString(TOKEN_KEY, data.getToken());
//    }
//
//    @Override
//    @NonNull
//    protected AuthInfo getValues() {
//        String login = getStringValue(LOGIN_KEY);
//        String token = getStringValue(TOKEN_KEY);
//        Timber.tag("TOKEN").d("Token %s", token);
//        return new AuthInfo(login, token);
//    }
}
