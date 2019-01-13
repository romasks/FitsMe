package ru.fitsme.android.Domain.Interactors.Auth;

import android.support.annotation.NonNull;

import ru.fitsme.android.Domain.Entities.AuthInfo;
import ru.fitsme.android.Domain.Entities.SignInInfo;

public interface ISignInRepository {
    @NonNull
    AuthInfo register(@NonNull SignInInfo signInInfo);

    @NonNull
    AuthInfo authorize(@NonNull SignInInfo signInInfo);
}
