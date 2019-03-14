package ru.fitsme.android.domain.entities.exceptions.user;

public class LoginOrPasswordNotValidException extends UserException {

    public static final int CODE = 100001;

    public LoginOrPasswordNotValidException() {
    }

    public LoginOrPasswordNotValidException(String message) {
        super(message);
    }
}
