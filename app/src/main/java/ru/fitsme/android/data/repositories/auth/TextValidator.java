package ru.fitsme.android.data.repositories.auth;

import androidx.annotation.Nullable;

import javax.inject.Inject;
import javax.inject.Singleton;

import ru.fitsme.android.domain.boundaries.auth.ITextValidator;

@Singleton
public class TextValidator implements ITextValidator {

    @Inject
    TextValidator() {
    }

    @Override
    public boolean checkLogin(@Nullable String login) {
        return login != null && login.length() >= 3;
    }

    @Override
    public boolean checkPassword(@Nullable String password) {
        return password != null && password.length() >= 6;
    }
}
