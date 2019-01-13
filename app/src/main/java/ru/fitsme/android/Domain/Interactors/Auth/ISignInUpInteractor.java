package ru.fitsme.android.Domain.Interactors.Auth;

import android.support.annotation.NonNull;

import io.reactivex.Completable;
import ru.fitsme.android.Domain.Entities.SignInInfo;

public interface ISignInUpInteractor {
    @NonNull
    Completable register(@NonNull SignInInfo signInInfo);

    @NonNull
    Completable authorize(@NonNull SignInInfo authInfo);

    @NonNull
    Completable authorizeAuto();
}
