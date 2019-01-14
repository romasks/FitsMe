package ru.fitsme.android.domain.interactors.auth;

import android.support.annotation.NonNull;

import ru.fitsme.android.domain.entities.AuthInfo;
import ru.fitsme.android.domain.entities.SignInInfo;

public interface ISignInRepository {
    @NonNull
    AuthInfo register(@NonNull SignInInfo signInInfo);

    @NonNull
    AuthInfo authorize(@NonNull SignInInfo signInInfo);
}
