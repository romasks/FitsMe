package ru.fitsme.android.domain.entities.exceptions.user;

public class TokenUserNotActiveException extends UserException {

    TokenUserNotActiveException(){}

    public TokenUserNotActiveException(String message) {
        super(message);
    }
}
