package ru.fitsme.android.domain.interactors.auth;

import android.support.annotation.NonNull;

import ru.fitsme.android.domain.entities.AuthInfo;
import ru.fitsme.android.domain.entities.SignInInfo;

public interface IUserInfoRepository {
    @NonNull
    SignInInfo getSignInInfo();

    void setSignInInfo(SignInInfo signInInfo);

    @NonNull
    AuthInfo getAuthInfo();

    void setAuthInfo(@NonNull AuthInfo authInfo);
}
