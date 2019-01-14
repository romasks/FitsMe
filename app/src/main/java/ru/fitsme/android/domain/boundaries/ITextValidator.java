package ru.fitsme.android.domain.boundaries;

import android.support.annotation.Nullable;

import ru.fitsme.android.domain.entities.exceptions.LoginIncorrectException;
import ru.fitsme.android.domain.entities.exceptions.PasswordIncorrectException;

public interface ITextValidator {

    void checkLogin(@Nullable String login) throws LoginIncorrectException;

    void checkPassword(@Nullable String password) throws PasswordIncorrectException;
}
