package ru.fitsme.android.domain.boundaries;

import android.support.annotation.NonNull;

import ru.fitsme.android.domain.entities.exceptions.DataNotFoundException;
import ru.fitsme.android.domain.entities.signinup.AuthInfo;
import ru.fitsme.android.domain.entities.signinup.SignInInfo;

public interface IUserInfoRepository {
    @NonNull
    SignInInfo getSignInInfo() throws DataNotFoundException;

    void setSignInInfo(SignInInfo signInInfo);

    @NonNull
    AuthInfo getAuthInfo() throws DataNotFoundException;

    void setAuthInfo(@NonNull AuthInfo authInfo);
}
