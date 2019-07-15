package ru.fitsme.android.data.repositories.auth;

import android.support.annotation.NonNull;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;
import ru.fitsme.android.data.frameworks.retrofit.WebLoader;
import ru.fitsme.android.data.frameworks.sharedpreferences.AuthInfoStorage;
import ru.fitsme.android.data.frameworks.sharedpreferences.IAuthInfoStorage;
import ru.fitsme.android.domain.boundaries.signinup.IAuthRepository;
import ru.fitsme.android.domain.entities.auth.SignInfo;
import ru.fitsme.android.domain.entities.auth.AuthInfo;

@Singleton
public class AuthRepository implements IAuthRepository {

    private WebLoader webLoader;
    private IAuthInfoStorage authInfoStorage;

    @Inject
    public AuthRepository(AuthInfoStorage authInfoStorage) {
        this.authInfoStorage = authInfoStorage;
    }

    @Override
    public AuthInfo getAuthInfo() {
        return authInfoStorage.getAuthInfo();
    }

    @Override
    public void setAuthInfo(AuthInfo authInfo) {
        authInfoStorage.setAuthInfo(authInfo);
    }

    @NonNull
    @Override
    public Single<AuthInfo> signIn(@NonNull SignInfo signInfo){
        return webLoader.signIn(signInfo);
    }

    @NonNull
    @Override
    public Single<AuthInfo> signUp(@NonNull SignInfo signInfo){
        return webLoader.signUp(signInfo);
    }
}
