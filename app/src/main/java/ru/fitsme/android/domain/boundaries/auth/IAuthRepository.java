package ru.fitsme.android.domain.boundaries.auth;

import ru.fitsme.android.domain.entities.auth.AuthInfo;

public interface IAuthRepository {
    AuthInfo getAuthInfo();
    void setAuthInfo(AuthInfo authInfo);
    void clearAuthInfo();
}
