package ru.fitsme.android.domain.boundaries.signinup;

import android.support.annotation.Nullable;

import ru.fitsme.android.data.entities.exceptions.user.WrongLoginException;
import ru.fitsme.android.data.entities.exceptions.user.WrongPasswordException;

public interface ITextValidator {

    void checkLogin(@Nullable String login) throws WrongLoginException;

    void checkPassword(@Nullable String password) throws WrongPasswordException;
}
