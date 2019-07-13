package ru.fitsme.android.domain.boundaries.signinup;

import android.support.annotation.NonNull;

import io.reactivex.Single;
import ru.fitsme.android.domain.entities.auth.SignInInfo;
import ru.fitsme.android.domain.entities.auth.AuthInfo;

public interface IAuthRepository {
//    @NonNull
//    AuthInfo register(@NonNull SignInInfo signInInfo) throws UserException;

    @NonNull
    Single<AuthInfo> signIn(@NonNull SignInInfo signInInfo);

    AuthInfo getAuthInfo();

    void setAuthInfo(AuthInfo authInfo);
}
