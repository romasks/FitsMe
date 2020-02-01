package ru.fitsme.android.domain.interactors.auth;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import io.reactivex.Single;
import ru.fitsme.android.domain.entities.auth.AuthInfo;
import ru.fitsme.android.domain.entities.auth.CodeResponse;
import ru.fitsme.android.domain.entities.auth.SignInUpResult;
import ru.fitsme.android.domain.interactors.BaseInteractor;

public interface ISignInteractor extends BaseInteractor {
    @NonNull
    Single<SignInUpResult> signIn(@Nullable String login, @Nullable String password);

    @NonNull
    Single<SignInUpResult> signUp(@Nullable String login, @Nullable String password);

    Single<CodeResponse> sendPhoneNumber(String phoneNumber);

    Single<AuthInfo> verifyCode(String code);
}
