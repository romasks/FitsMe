package ru.fitsme.android.domain.entities.exceptions.user;

public class InvalidTokenException extends UserException {

    public InvalidTokenException(){}

    public InvalidTokenException(String message) {
        super(message);
    }
}
