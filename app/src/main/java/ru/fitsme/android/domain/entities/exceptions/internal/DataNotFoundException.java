package ru.fitsme.android.domain.entities.exceptions.internal;

public class DataNotFoundException extends InternalException {
    public DataNotFoundException(String message) {
        super(message);
    }

    public DataNotFoundException() {
    }
}
