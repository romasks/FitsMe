package ru.fitsme.android.domain.boundaries.signinup;

import android.support.annotation.NonNull;

import io.reactivex.Single;
import ru.fitsme.android.domain.entities.auth.SignInfo;
import ru.fitsme.android.domain.entities.auth.AuthInfo;

public interface IAuthRepository {
    @NonNull
    Single<AuthInfo> signUp(@NonNull SignInfo signInfo);

    @NonNull
    Single<AuthInfo> signIn(@NonNull SignInfo signInfo);

    AuthInfo getAuthInfo();

    void setAuthInfo(AuthInfo authInfo);
}
