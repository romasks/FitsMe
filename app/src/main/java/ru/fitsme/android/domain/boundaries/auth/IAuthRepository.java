package ru.fitsme.android.domain.boundaries.auth;

import android.support.annotation.NonNull;

import io.reactivex.Single;
import ru.fitsme.android.domain.entities.auth.SignInfo;
import ru.fitsme.android.domain.entities.auth.AuthInfo;

public interface IAuthRepository {

    AuthInfo getAuthInfo();

    void setAuthInfo(AuthInfo authInfo);
}
