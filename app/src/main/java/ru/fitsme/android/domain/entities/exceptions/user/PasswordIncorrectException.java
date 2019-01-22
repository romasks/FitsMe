package ru.fitsme.android.domain.entities.exceptions.user;

public class PasswordIncorrectException extends UserException {
    public PasswordIncorrectException(String message) {
        super(message);
    }

    public PasswordIncorrectException() {

    }
}
