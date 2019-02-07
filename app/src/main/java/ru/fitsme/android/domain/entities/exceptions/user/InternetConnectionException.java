package ru.fitsme.android.domain.entities.exceptions.user;

public class InternetConnectionException extends UserException {
    public InternetConnectionException(String message) {
        super(message);
    }

    public InternetConnectionException() {

    }
}
