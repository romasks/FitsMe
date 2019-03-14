package ru.fitsme.android.domain.entities.exceptions.user;

public class WrongTokenException extends UserException {

    public static final int CODE = 100005;

    public WrongTokenException(){}

    public WrongTokenException(String message) {
        super(message);
    }
}
