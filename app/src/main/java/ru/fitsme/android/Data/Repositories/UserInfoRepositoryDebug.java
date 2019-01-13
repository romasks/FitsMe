package ru.fitsme.android.Data.Repositories;

import android.support.annotation.NonNull;

import javax.inject.Inject;
import javax.inject.Singleton;

import ru.fitsme.android.Domain.Entities.AuthInfo;
import ru.fitsme.android.Domain.Entities.SignInInfo;
import ru.fitsme.android.Domain.Interactors.Auth.IUserInfoRepository;

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
