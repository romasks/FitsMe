package ru.fitsme.android.data.repositories.auth;

import android.support.annotation.Nullable;

import javax.inject.Inject;
import javax.inject.Singleton;

import ru.fitsme.android.domain.boundaries.auth.ITextValidator;

@Singleton
public class TextValidator implements ITextValidator {

    @Inject
    public TextValidator() {
    }

    @Override
    public boolean checkLogin(@Nullable String login){
        if (login == null || login.length() < 3) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public boolean checkPassword(@Nullable String password){
        if (password == null || password.length() < 6) {
            return false;
        } else {
            return true;
        }
    }
}
