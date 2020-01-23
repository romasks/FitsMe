package ru.fitsme.android.domain.boundaries.auth;

import io.reactivex.Single;
import ru.fitsme.android.domain.entities.auth.AuthInfo;

public interface IAuthRepository {

    AuthInfo getAuthInfo();

    void setAuthInfo(AuthInfo authInfo);

    Single<Boolean> sendPhoneNumber(String phoneNumber);
}
