package ru.fitsme.android.domain.entities.exceptions;

public class PasswordNotValidException extends AppException {
    public PasswordNotValidException(String message) {
        super(message);
    }

    public PasswordNotValidException() {
    }
}
