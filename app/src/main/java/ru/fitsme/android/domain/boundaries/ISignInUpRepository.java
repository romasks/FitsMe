package ru.fitsme.android.domain.boundaries;

import android.support.annotation.NonNull;

import ru.fitsme.android.domain.entities.exceptions.InternetConnectionException;
import ru.fitsme.android.domain.entities.exceptions.LoginAlreadyInUseException;
import ru.fitsme.android.domain.entities.exceptions.LoginIncorrectException;
import ru.fitsme.android.domain.entities.exceptions.LoginNotFoundException;
import ru.fitsme.android.domain.entities.exceptions.PasswordIncorrectException;
import ru.fitsme.android.domain.entities.exceptions.ServerInternalException;
import ru.fitsme.android.domain.entities.signinup.AuthInfo;
import ru.fitsme.android.domain.entities.signinup.SignInInfo;

public interface ISignInUpRepository {
    @NonNull
    AuthInfo register(@NonNull SignInInfo signInInfo) throws LoginIncorrectException,
            LoginAlreadyInUseException, PasswordIncorrectException, ServerInternalException,
            InternetConnectionException;

    @NonNull
    AuthInfo authorize(@NonNull SignInInfo signInInfo) throws LoginIncorrectException,
            LoginNotFoundException, PasswordIncorrectException, ServerInternalException,
            InternetConnectionException;
}
