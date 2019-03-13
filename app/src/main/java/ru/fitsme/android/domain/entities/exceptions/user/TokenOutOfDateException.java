package ru.fitsme.android.domain.entities.exceptions.user;

public class TokenOutOfDateException extends UserException {

    public TokenOutOfDateException(){}

    public TokenOutOfDateException(String message) {
        super(message);
    }
}
