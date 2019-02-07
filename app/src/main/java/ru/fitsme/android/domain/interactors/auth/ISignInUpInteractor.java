package ru.fitsme.android.domain.interactors.auth;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import io.reactivex.Single;
import ru.fitsme.android.domain.entities.signinup.AutoSignInInfo;
import ru.fitsme.android.domain.entities.signinup.SignInInfo;
import ru.fitsme.android.domain.entities.signinup.SignInUpResult;

public interface ISignInUpInteractor {
    @NonNull
    Single<SignInUpResult> register(@Nullable String login, @Nullable String password);

    @NonNull
    Single<SignInUpResult> authorize(@Nullable String login, @Nullable String password);

    @NonNull
    Single<SignInUpResult> checkLogin(@Nullable String login);

    @NonNull
    Single<SignInUpResult> checkPassword(@Nullable String password);

    @NonNull
    Single<AutoSignInInfo> getAutoSignInInfo();

    @NonNull
    Single<SignInUpResult> authorize(@NonNull SignInInfo signInInfo);
}
