package ru.fitsme.android.domain.entities.exceptions;

public class LoginNotFoundException extends AppException implements UserError {
    public LoginNotFoundException(String message) {
        super(message);
    }
}
