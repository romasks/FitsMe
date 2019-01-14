package ru.fitsme.android.data.repositories;

import javax.inject.Inject;
import javax.inject.Singleton;

import ru.fitsme.android.domain.interactors.auth.ITextValidator;

@Singleton
public class TextValidatorDebug implements ITextValidator {

    @Inject
    public TextValidatorDebug() {
    }

    @Override
    public boolean checkLogin(String login) {
        return login != null && login.length() > 2;
    }

    @Override
    public boolean checkPassword(String password) {
        return password != null && password.length() > 2;
    }
}
