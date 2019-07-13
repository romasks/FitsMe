package ru.fitsme.android.domain.boundaries.signinup;

import android.support.annotation.Nullable;

import ru.fitsme.android.domain.entities.exceptions.user.WrongLoginException;
import ru.fitsme.android.domain.entities.exceptions.user.WrongPasswordException;

public interface ITextValidator {

    boolean checkLogin(@Nullable String login);

    boolean checkPassword(@Nullable String password);
}
