package ru.fitsme.android.domain.entities.exceptions.user;

public class WrongTokenException extends UserException {

    public WrongTokenException(){}

    public WrongTokenException(String message) {
        super(message);
    }
}
