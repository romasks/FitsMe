package ru.fitsme.android.domain.entities.exceptions;

public class ConvertHashException extends AppException implements UserError {
    public ConvertHashException(String message) {
        super(message);
    }

    public ConvertHashException() {
    }
}
