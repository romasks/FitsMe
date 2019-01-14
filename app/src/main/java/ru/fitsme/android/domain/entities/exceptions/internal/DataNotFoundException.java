package ru.fitsme.android.domain.entities.exceptions.internal;

import ru.fitsme.android.domain.entities.exceptions.AppException;

public class DataNotFoundException extends AppException {
    public DataNotFoundException(String message) {
        super(message);
    }

    public DataNotFoundException() {
    }
}
