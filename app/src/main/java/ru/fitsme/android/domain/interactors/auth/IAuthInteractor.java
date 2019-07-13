package ru.fitsme.android.domain.interactors.auth;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import io.reactivex.Single;
import io.reactivex.disposables.Disposable;
import ru.fitsme.android.domain.entities.auth.AuthInfo;
import ru.fitsme.android.domain.entities.auth.SignInInfo;
import ru.fitsme.android.domain.entities.auth.SignInUpResult;
import ru.fitsme.android.domain.interactors.BaseInteractor;

public interface IAuthInteractor extends BaseInteractor {

    Single<AuthInfo> getAuthInfo();

    @NonNull
    Single<SignInUpResult> signIn(@Nullable String login, @Nullable String password);

//    @NonNull
//    Single<SignInUpResult> signIn(@NonNull SignInInfo signInInfo);
//

//    @NonNull
//    Single<SignInUpResult> register(@Nullable String login, @Nullable String password);
//
//    @NonNull
//    Single<SignInUpResult> checkLogin(@Nullable String login);
//
//    @NonNull
//    Single<SignInUpResult> checkPassword(@Nullable String password);
//
//    @NonNull
//    Single<SignInInfo> getAuthInfo();
//


}
