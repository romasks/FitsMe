package ru.fitsme.android.domain.entities.exceptions.user;

public class TokenNotSearchUser extends UserException {

    public static final int CODE = 100010;

    public TokenNotSearchUser(){}

    public TokenNotSearchUser(String message) {
        super(message);
    }
}
