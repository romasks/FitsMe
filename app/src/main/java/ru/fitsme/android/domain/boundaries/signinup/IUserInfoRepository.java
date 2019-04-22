package ru.fitsme.android.domain.boundaries.signinup;

import android.support.annotation.NonNull;

import ru.fitsme.android.data.entities.exceptions.internal.DataNotFoundException;
import ru.fitsme.android.data.entities.response.signinup.AuthInfo;
import ru.fitsme.android.data.entities.response.signinup.SignInInfo;

public interface IUserInfoRepository {
    @NonNull
    SignInInfo getSignInInfo() throws DataNotFoundException;

    void setSignInInfo(@NonNull SignInInfo signInInfo);

    @NonNull
    AuthInfo getAuthInfo() throws DataNotFoundException;

    void setAuthInfo(@NonNull AuthInfo authInfo);
}
