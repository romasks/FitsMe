package ru.fitsme.android.domain.entities.exceptions;

public class LoginNotFoundException extends AppException {
    public LoginNotFoundException(String message) {
        super(message);
    }
}
