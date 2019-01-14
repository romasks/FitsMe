package ru.fitsme.android.data.repositories;

import android.support.annotation.NonNull;

import javax.inject.Inject;
import javax.inject.Singleton;

import ru.fitsme.android.data.frameworks.retrofit.WebLoader;
import ru.fitsme.android.domain.boundaries.ISignInUpRepository;
import ru.fitsme.android.domain.entities.exceptions.InternetConnectionException;
import ru.fitsme.android.domain.entities.exceptions.LoginAlreadyInUseException;
import ru.fitsme.android.domain.entities.exceptions.LoginIncorrectException;
import ru.fitsme.android.domain.entities.exceptions.ServerInternalException;
import ru.fitsme.android.domain.entities.signinup.AuthInfo;
import ru.fitsme.android.domain.entities.signinup.SignInInfo;

@Singleton
public class SignInUpRepository implements ISignInUpRepository {

    private WebLoader webLoader;

    @Inject
    public SignInUpRepository(WebLoader webLoader) {
        this.webLoader = webLoader;
    }

    @NonNull
    @Override
    public AuthInfo register(@NonNull SignInInfo signInInfo) throws LoginIncorrectException,
            LoginAlreadyInUseException, ServerInternalException, InternetConnectionException {
        return webLoader.signUp(signInInfo);
    }

    @NonNull
    @Override
    public AuthInfo authorize(@NonNull SignInInfo signInInfo) throws LoginIncorrectException,
            ServerInternalException, InternetConnectionException {
        return webLoader.signIn(signInInfo);
    }

    @NonNull
    private AuthInfo getAuthInfo(@NonNull SignInInfo signInInfo) {
        return new AuthInfo(signInInfo.getLogin(), signInInfo.getPasswordHash() + "_token");
    }
}
