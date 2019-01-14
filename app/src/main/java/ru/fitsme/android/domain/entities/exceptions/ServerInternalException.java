package ru.fitsme.android.domain.entities.exceptions;

public class ServerInternalException extends AppException implements UserError {
    public ServerInternalException(String message) {
        super(message);
    }
}
