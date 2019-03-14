package ru.fitsme.android.domain.entities.exceptions.internal;

import ru.fitsme.android.domain.entities.exceptions.AppException;

public class InternalException extends AppException {

    public static final int CODE = 900001;

    public InternalException(String message) {
        super(message);
    }

    public InternalException() {
    }
}
