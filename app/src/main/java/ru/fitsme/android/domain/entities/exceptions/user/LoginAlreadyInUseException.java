package ru.fitsme.android.domain.entities.exceptions.user;

public class LoginAlreadyInUseException extends UserException {
    public LoginAlreadyInUseException(String message) {
        super(message);
    }

    public LoginAlreadyInUseException() {

    }
}
