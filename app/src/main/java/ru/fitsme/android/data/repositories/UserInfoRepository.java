package ru.fitsme.android.data.repositories;

import android.support.annotation.NonNull;

import javax.inject.Inject;
import javax.inject.Singleton;

import ru.fitsme.android.data.frameworks.sharedpreferences.AuthInfoStorage;
import ru.fitsme.android.data.frameworks.sharedpreferences.SignInUpInfoStorage;
import ru.fitsme.android.domain.boundaries.clothes.IUserInfoRepository;
import ru.fitsme.android.domain.entities.exceptions.internal.DataNotFoundException;
import ru.fitsme.android.domain.entities.signinup.AuthInfo;
import ru.fitsme.android.domain.entities.signinup.SignInInfo;

@Singleton
public class UserInfoRepository implements IUserInfoRepository {

    private SignInUpInfoStorage signInUpInfoStorage;
    private AuthInfoStorage authInfoStorage;

    @Inject
    public UserInfoRepository(SignInUpInfoStorage signInUpInfoStorage, AuthInfoStorage authInfoStorage) {
        this.signInUpInfoStorage = signInUpInfoStorage;
        this.authInfoStorage = authInfoStorage;
    }

    @Override
    @NonNull
    public SignInInfo getSignInInfo() throws DataNotFoundException {
        return signInUpInfoStorage.getData();
    }

    @Override
    public void setSignInInfo(@NonNull SignInInfo signInInfo) {
        signInUpInfoStorage.setData(signInInfo);
    }

    @Override
    @NonNull
    public AuthInfo getAuthInfo() throws DataNotFoundException {
        return authInfoStorage.getData();
    }

    @Override
    public void setAuthInfo(@NonNull AuthInfo authInfo) {
        authInfoStorage.setData(authInfo);
    }
}
