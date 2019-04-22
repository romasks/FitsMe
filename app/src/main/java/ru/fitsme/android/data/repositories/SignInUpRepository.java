package ru.fitsme.android.data.repositories;

import android.support.annotation.NonNull;

import javax.inject.Inject;
import javax.inject.Singleton;

import ru.fitsme.android.data.api.WebLoader;
import ru.fitsme.android.domain.boundaries.signinup.ISignInUpRepository;
import ru.fitsme.android.data.entities.exceptions.user.UserException;
import ru.fitsme.android.data.entities.response.signinup.AuthInfo;
import ru.fitsme.android.data.entities.response.signinup.SignInInfo;

@Singleton
public class SignInUpRepository implements ISignInUpRepository {

    private WebLoader webLoader;

    @Inject
    public SignInUpRepository(WebLoader webLoader) {
        this.webLoader = webLoader;
    }

    @NonNull
    @Override
    public AuthInfo register(@NonNull SignInInfo signInInfo) throws UserException {
        return webLoader.signUp(signInInfo);
    }

    @NonNull
    @Override
    public AuthInfo authorize(@NonNull SignInInfo signInInfo) throws UserException {
        return webLoader.signIn(signInInfo);
    }
}
