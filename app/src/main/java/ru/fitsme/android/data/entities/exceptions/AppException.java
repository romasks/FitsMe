package ru.fitsme.android.data.entities.exceptions;

public abstract class AppException extends Exception {
    public AppException(String message) {
        super(message);
    }

    public AppException() {

    }
}
