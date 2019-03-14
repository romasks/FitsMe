package ru.fitsme.android.domain.entities.exceptions.user;

public class LoginAlreadyExistException extends UserException {

    public static final int CODE = 100002;

    public LoginAlreadyExistException(String message) {
        super(message);
    }

    public LoginAlreadyExistException() {

    }
}
