package ru.fitsme.android.data.repositories;

import android.support.annotation.Nullable;

import javax.inject.Inject;
import javax.inject.Singleton;

import ru.fitsme.android.domain.boundaries.signinup.ITextValidator;
import ru.fitsme.android.domain.entities.exceptions.user.WrongLoginException;
import ru.fitsme.android.domain.entities.exceptions.user.WrongPasswordException;

@Singleton
public class TextValidator implements ITextValidator {

    @Inject
    public TextValidator() {
    }

    @Override
    public void checkLogin(@Nullable String login) throws WrongLoginException {
        if (login == null || login.length() < 3)
            throw new WrongLoginException();
    }

    @Override
    public void checkPassword(@Nullable String password) throws WrongPasswordException {
        if (password == null || password.length() < 6)
            throw new WrongPasswordException();
    }
}
