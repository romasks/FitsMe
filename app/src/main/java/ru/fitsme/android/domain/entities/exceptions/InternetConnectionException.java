package ru.fitsme.android.domain.entities.exceptions;

public class InternetConnectionException extends AppException implements UserError {
    public InternetConnectionException(String message) {
        super(message);
    }

    public InternetConnectionException() {

    }
}
