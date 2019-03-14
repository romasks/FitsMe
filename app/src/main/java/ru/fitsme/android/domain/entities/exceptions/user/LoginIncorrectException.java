package ru.fitsme.android.domain.entities.exceptions.user;

public class LoginIncorrectException extends UserException {

    public static final int CODE = 100003;

    public LoginIncorrectException(String message) {
        super(message);
    }

    public LoginIncorrectException() {
    }
}
