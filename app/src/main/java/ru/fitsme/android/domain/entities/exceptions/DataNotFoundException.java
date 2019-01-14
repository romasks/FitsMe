package ru.fitsme.android.domain.entities.exceptions;

public class DataNotFoundException extends AppException {
    public DataNotFoundException(String message) {
        super(message);
    }

    public DataNotFoundException() {
    }
}
