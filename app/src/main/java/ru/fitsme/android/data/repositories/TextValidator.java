package ru.fitsme.android.data.repositories;

import android.support.annotation.Nullable;

import javax.inject.Inject;
import javax.inject.Singleton;

import ru.fitsme.android.domain.boundaries.ITextValidator;
import ru.fitsme.android.domain.entities.exceptions.user.LoginIncorrectException;
import ru.fitsme.android.domain.entities.exceptions.user.PasswordIncorrectException;

@Singleton
public class TextValidator implements ITextValidator {

    @Inject
    public TextValidator() {
    }

    @Override
    public void checkLogin(@Nullable String login) throws LoginIncorrectException {
        if (login == null || login.length() < 3)
            throw new LoginIncorrectException();
    }

    @Override
    public void checkPassword(@Nullable String password) throws PasswordIncorrectException {
        if (password == null || password.length() < 6)
            throw new PasswordIncorrectException();
    }
}
