package ru.fitsme.android.domain.entities.exceptions;

public class LoginAlreadyInUseException extends AppException {
    public LoginAlreadyInUseException(String message) {
        super(message);
    }
}
