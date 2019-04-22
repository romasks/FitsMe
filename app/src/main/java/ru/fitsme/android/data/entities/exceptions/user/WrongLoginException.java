package ru.fitsme.android.data.entities.exceptions.user;

public class WrongLoginException extends UserException {

    public static final int CODE = 100003;

    public WrongLoginException(String message) {
        super(message);
    }

    public WrongLoginException() {
    }
}
