package ru.fitsme.android.data.repositories;

import android.support.annotation.NonNull;

import javax.inject.Inject;
import javax.inject.Singleton;

import ru.fitsme.android.domain.boundaries.ISignInUpRepository;
import ru.fitsme.android.domain.entities.signinup.AuthInfo;
import ru.fitsme.android.domain.entities.signinup.SignInInfo;

@Singleton
public class SignInUpRepositoryDebug implements ISignInUpRepository {

    @Inject
    public SignInUpRepositoryDebug() {
    }

    @NonNull
    @Override
    public AuthInfo register(@NonNull SignInInfo signInInfo) {
        return getAuthInfo(signInInfo);
    }

    @NonNull
    @Override
    public AuthInfo authorize(@NonNull SignInInfo signInInfo) {
        return getAuthInfo(signInInfo);
    }

    @NonNull
    private AuthInfo getAuthInfo(@NonNull SignInInfo signInInfo) {
        return new AuthInfo(signInInfo.getLogin(), signInInfo.getPasswordHash() + "_token");
    }
}
