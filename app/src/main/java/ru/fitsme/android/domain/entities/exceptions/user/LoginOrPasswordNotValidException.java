package ru.fitsme.android.domain.entities.exceptions.user;

public class LoginOrPasswordNotValidException extends UserException {
    public LoginOrPasswordNotValidException() {
    }

    public LoginOrPasswordNotValidException(String message) {
        super(message);
    }
}
