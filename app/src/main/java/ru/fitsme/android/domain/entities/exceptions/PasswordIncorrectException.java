package ru.fitsme.android.domain.entities.exceptions;

public class PasswordIncorrectException extends AppException implements UserError {
    public PasswordIncorrectException(String message) {
        super(message);
    }

    public PasswordIncorrectException() {

    }
}
