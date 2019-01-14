package ru.fitsme.android.domain.interactors.auth;

import android.support.annotation.NonNull;

import io.reactivex.Completable;
import ru.fitsme.android.domain.entities.SignInInfo;

public interface ISignInUpInteractor {
    @NonNull
    Completable register(@NonNull SignInInfo signInInfo);

    @NonNull
    Completable authorize(@NonNull SignInInfo authInfo);

    @NonNull
    Completable authorizeAuto();

    boolean checkLogin(String login);

    boolean checkPassword(String password);
}
