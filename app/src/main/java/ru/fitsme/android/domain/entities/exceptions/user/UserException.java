package ru.fitsme.android.domain.entities.exceptions.user;

import ru.fitsme.android.domain.entities.exceptions.AppException;

//Исключения, обрабатываемые для уведомления пользователя
public class UserException extends AppException {
    public UserException(String message) {
        super(message);
    }

    public UserException() {
    }
}
