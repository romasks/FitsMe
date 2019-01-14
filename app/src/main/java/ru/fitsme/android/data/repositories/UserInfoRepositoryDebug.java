package ru.fitsme.android.data.repositories;

import android.support.annotation.NonNull;

import javax.inject.Inject;
import javax.inject.Singleton;

import ru.fitsme.android.domain.entities.AuthInfo;
import ru.fitsme.android.domain.entities.SignInInfo;
import ru.fitsme.android.domain.interactors.auth.IUserInfoRepository;

@Singleton
public class UserInfoRepositoryDebug implements IUserInfoRepository {

    private SignInInfo signInInfo;
    private AuthInfo authInfo;

    @Inject
    public UserInfoRepositoryDebug() {
    }

    @NonNull
    @Override
    public SignInInfo getSignInInfo() {
        if (signInInfo == null)
            throw new RuntimeException("Not found " + SignInInfo.class.getSimpleName());
        return signInInfo;
    }

    @Override
    public void setSignInInfo(SignInInfo signInInfo) {
        this.signInInfo = signInInfo;
    }

    @NonNull
    @Override
    public AuthInfo getAuthInfo() {
        if (authInfo == null)
            throw new RuntimeException("Not found " + AuthInfo.class.getSimpleName());
        return authInfo;
    }

    @Override
    public void setAuthInfo(@NonNull AuthInfo authInfo) {
        this.authInfo = authInfo;
    }
}
