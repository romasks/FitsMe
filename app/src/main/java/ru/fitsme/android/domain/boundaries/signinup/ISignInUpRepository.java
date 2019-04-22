package ru.fitsme.android.domain.boundaries.signinup;

import android.support.annotation.NonNull;

import ru.fitsme.android.data.entities.exceptions.user.UserException;
import ru.fitsme.android.data.entities.response.signinup.AuthInfo;
import ru.fitsme.android.data.entities.response.signinup.SignInInfo;

public interface ISignInUpRepository {
    @NonNull
    AuthInfo register(@NonNull SignInInfo signInInfo) throws UserException;

    @NonNull
    AuthInfo authorize(@NonNull SignInInfo signInInfo) throws UserException;
}
