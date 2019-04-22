package ru.fitsme.android.data.entities.exceptions.user;

public class TokenUserNotActiveException extends UserException {

    public static final int CODE = 100011;

    TokenUserNotActiveException(){}

    public TokenUserNotActiveException(String message) {
        super(message);
    }
}
