package ru.fitsme.android.domain.entities.exceptions.user;

public class ForbiddenPhoneNumber extends UserException {

    public static final int CODE = 100022;

    public ForbiddenPhoneNumber(String message) {
        super(message);
    }

    public ForbiddenPhoneNumber() {
    }
}
