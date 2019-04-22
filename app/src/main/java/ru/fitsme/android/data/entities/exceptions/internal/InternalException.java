package ru.fitsme.android.data.entities.exceptions.internal;

import ru.fitsme.android.data.entities.exceptions.AppException;

public class InternalException extends AppException {

    public static final int CODE = 900001;

    public InternalException(String message) {
        super(message);
    }

    public InternalException() {
    }
}
