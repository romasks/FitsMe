package ru.fitsme.android.domain.entities.auth;

import ru.fitsme.android.domain.entities.exceptions.user.UserException;

public class AuthInfo {
    private boolean isAuth;
    private String login;
    private String token;
    private UserException error;

    public AuthInfo(String login, String token) {
        this.login = login;
        this.token = token;
        if (login == null || token == null){
            this.isAuth = false;
        } else {
            this.isAuth = true;
        }
    }

    public AuthInfo(UserException error){
        this.isAuth = false;
        this.error = error;
    }

    public String getLogin() {
        return login;
    }

    public String getToken() {
        return token;
    }

    public UserException getError() {
        return error;
    }

    public boolean isAuth() {
        return isAuth;
    }
}
