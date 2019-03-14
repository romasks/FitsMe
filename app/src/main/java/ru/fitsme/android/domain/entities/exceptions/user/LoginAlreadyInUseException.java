package ru.fitsme.android.domain.entities.exceptions.user;

public class LoginAlreadyInUseException extends UserException {

    public static final int CODE = 100002;

    public LoginAlreadyInUseException(String message) {
        super(message);
    }

    public LoginAlreadyInUseException() {

    }
}
