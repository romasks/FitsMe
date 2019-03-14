package ru.fitsme.android.domain.entities.exceptions.user;

public class TokenOutOfDateException extends UserException {

    public static final int CODE = 100007;

    public TokenOutOfDateException(){}

    public TokenOutOfDateException(String message) {
        super(message);
    }
}
