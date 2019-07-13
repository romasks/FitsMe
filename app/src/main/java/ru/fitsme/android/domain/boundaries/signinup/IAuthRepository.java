package ru.fitsme.android.domain.boundaries.signinup;

import android.support.annotation.NonNull;

import ru.fitsme.android.domain.entities.exceptions.user.UserException;
import ru.fitsme.android.domain.entities.auth.AuthInfo;

public interface IAuthRepository {
//    @NonNull
//    AuthInfo register(@NonNull SignInInfo signInInfo) throws UserException;

//    @NonNull
//    AuthInfo authorize(@NonNull AuthInfo authInfo);

    AuthInfo getAuthInfo();
}
