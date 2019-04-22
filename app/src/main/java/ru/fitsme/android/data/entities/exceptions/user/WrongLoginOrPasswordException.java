package ru.fitsme.android.data.entities.exceptions.user;

public class WrongLoginOrPasswordException extends UserException {

    public static final int CODE = 100001;

    public WrongLoginOrPasswordException() {
    }

    public WrongLoginOrPasswordException(String message) {
        super(message);
    }
}
