package ru.fitsme.android.data.repositories.auth;

import javax.inject.Inject;
import javax.inject.Singleton;

import ru.fitsme.android.app.App;
import ru.fitsme.android.data.frameworks.sharedpreferences.IAuthInfoStorage;
import ru.fitsme.android.domain.boundaries.auth.IAuthRepository;
import ru.fitsme.android.domain.entities.auth.AuthInfo;

@Singleton
public class AuthRepository implements IAuthRepository {

    private IAuthInfoStorage authInfoStorage;

    @Inject
    AuthRepository(IAuthInfoStorage authInfoStorage) {
        this.authInfoStorage = authInfoStorage;
    }

    @Override
    public AuthInfo getAuthInfo() {
        AuthInfo authInfo = App.getInstance().getAuthInfo();
        if (authInfo == null){
            updateAppAuthInfo();
            authInfo = App.getInstance().getAuthInfo();
        }
        return authInfo;
    }

    @Override
    public void setAuthInfo(AuthInfo authInfo) {
        authInfoStorage.setAuthInfo(authInfo);
        updateAppAuthInfo();
    }

    private void updateAppAuthInfo(){
        AuthInfo authInfo = authInfoStorage.getAuthInfo();
        App.getInstance().setAuthInfo(authInfo);
    }
}
