package ru.fitsme.android.data.entities.response.signinup;

import timber.log.Timber;

public class AuthInfo {
    private String login;
    private String token;

    public AuthInfo(String login, String token) {
        this.login = login;
        this.token = token;
    }

    public String getLogin() {
        return login;
    }

    public String getToken() {
        Timber.tag(getClass().getName()).d("TOKEN:");
        Timber.tag(getClass().getName()).d(token);
        return token;
    }
}
