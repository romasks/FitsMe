package ru.fitsme.android.domain.entities.exceptions.user;

public class WrongPhoneNumber extends UserException {

    public static final int CODE = 100021;

    public WrongPhoneNumber(String message) {
        super(message);
    }

    public WrongPhoneNumber() {
    }
}
