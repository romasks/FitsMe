package ru.fitsme.android.domain.entities.exceptions.user;

public class PasswordIncorrectException extends UserException {

    public static final int CODE = 100004;

    public PasswordIncorrectException(String message) {
        super(message);
    }

    public PasswordIncorrectException() {

    }
}
