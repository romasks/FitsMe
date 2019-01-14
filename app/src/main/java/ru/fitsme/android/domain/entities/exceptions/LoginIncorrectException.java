package ru.fitsme.android.domain.entities.exceptions;

public class LoginIncorrectException extends AppException {
    public LoginIncorrectException(String message) {
        super(message);
    }

    public LoginIncorrectException() {
    }
}
