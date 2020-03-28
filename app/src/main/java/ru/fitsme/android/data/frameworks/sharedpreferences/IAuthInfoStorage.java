package ru.fitsme.android.data.frameworks.sharedpreferences;

import ru.fitsme.android.domain.entities.auth.AuthInfo;

public interface IAuthInfoStorage {
    AuthInfo getAuthInfo();
    void setAuthInfo(AuthInfo authInfo);
    void clearAuthInfo();
}
