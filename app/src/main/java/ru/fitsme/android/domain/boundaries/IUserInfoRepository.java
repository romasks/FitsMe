package ru.fitsme.android.domain.boundaries;

import android.support.annotation.NonNull;

import java.io.IOException;

import ru.fitsme.android.domain.entities.signinup.AuthInfo;
import ru.fitsme.android.domain.entities.signinup.SignInInfo;

public interface IUserInfoRepository {
    @NonNull
    SignInInfo getSignInInfo() throws IOException;

    void setSignInInfo(SignInInfo signInInfo);

    @NonNull
    AuthInfo getAuthInfo() throws IOException;

    void setAuthInfo(@NonNull AuthInfo authInfo);
}
