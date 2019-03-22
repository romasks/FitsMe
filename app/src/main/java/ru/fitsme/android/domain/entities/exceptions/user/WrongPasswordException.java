package ru.fitsme.android.domain.entities.exceptions.user;

public class WrongPasswordException extends UserException {

    public static final int CODE = 100004;

    public WrongPasswordException(String message) {
        super(message);
    }

    public WrongPasswordException() {

    }
}
