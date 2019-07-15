package ru.fitsme.android.domain.boundaries.auth;

import android.support.annotation.NonNull;

import io.reactivex.Single;
import ru.fitsme.android.domain.entities.auth.AuthInfo;
import ru.fitsme.android.domain.entities.auth.SignInfo;

public interface ISignRepository {

    @NonNull
    Single<AuthInfo> signUp(@NonNull SignInfo signInfo);

    @NonNull
    Single<AuthInfo> signIn(@NonNull SignInfo signInfo);
}
