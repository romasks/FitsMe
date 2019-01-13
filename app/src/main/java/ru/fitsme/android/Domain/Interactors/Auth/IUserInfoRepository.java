package ru.fitsme.android.Domain.Interactors.Auth;

import android.support.annotation.NonNull;

import ru.fitsme.android.Domain.Entities.AuthInfo;
import ru.fitsme.android.Domain.Entities.SignInInfo;

public interface IUserInfoRepository {
    @NonNull
    SignInInfo getSignInInfo();

    void setSignInInfo(SignInInfo signInInfo);

    @NonNull
    AuthInfo getAuthInfo();

    void setAuthInfo(@NonNull AuthInfo authInfo);
}
