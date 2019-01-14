package ru.fitsme.android.domain.entities.exceptions;

public abstract class AppException extends Exception {
    public AppException(String message) {
        super(message);
    }

    public AppException() {

    }
}
