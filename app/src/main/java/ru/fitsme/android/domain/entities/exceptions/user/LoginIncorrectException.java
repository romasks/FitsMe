package ru.fitsme.android.domain.entities.exceptions.user;

public class LoginIncorrectException extends UserException {
    public LoginIncorrectException(String message) {
        super(message);
    }

    public LoginIncorrectException() {
    }
}
