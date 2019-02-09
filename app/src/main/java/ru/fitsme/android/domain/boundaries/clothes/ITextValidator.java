package ru.fitsme.android.domain.boundaries.clothes;

import android.support.annotation.Nullable;

import ru.fitsme.android.domain.entities.exceptions.user.LoginIncorrectException;
import ru.fitsme.android.domain.entities.exceptions.user.PasswordIncorrectException;

public interface ITextValidator {

    void checkLogin(@Nullable String login) throws LoginIncorrectException;

    void checkPassword(@Nullable String password) throws PasswordIncorrectException;
}
