package ru.fitsme.android.data.repositories;

import android.support.annotation.NonNull;

import javax.inject.Inject;
import javax.inject.Singleton;

import ru.fitsme.android.data.frameworks.retrofit.WebLoader;
import ru.fitsme.android.data.frameworks.sharedpreferences.AuthInfoStorage;
import ru.fitsme.android.data.frameworks.sharedpreferences.IAuthInfoStorage;
import ru.fitsme.android.domain.boundaries.signinup.IAuthRepository;
import ru.fitsme.android.domain.entities.exceptions.user.UserException;
import ru.fitsme.android.domain.entities.auth.AuthInfo;

@Singleton
public class AuthRepository implements IAuthRepository {

//    private WebLoader webLoader;
    private IAuthInfoStorage authInfoStorage;

    @Inject
    public AuthRepository(AuthInfoStorage authInfoStorage) {
        this.authInfoStorage = authInfoStorage;
    }

//    @NonNull
//    @Override
//    public AuthInfo register(@NonNull SignInInfo signInInfo) throws UserException {
//        return webLoader.signUp(signInInfo);
//    }

    @Override
    public AuthInfo getAuthInfo() {
        return authInfoStorage.getAuthInfo();
    }

//    @NonNull
//    @Override
//    public AuthInfo authorize(@NonNull SignInInfo signInInfo) throws UserException {
//        return webLoader.getAuthInfo(signInInfo);
//    }


}
