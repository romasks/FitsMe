package ru.fitsme.android.domain.interactors.auth;

import io.reactivex.Single;
import ru.fitsme.android.domain.entities.auth.AuthInfo;
import ru.fitsme.android.domain.interactors.BaseInteractor;

public interface IAuthInteractor extends BaseInteractor {
    Single<AuthInfo> getAuthInfo();

    Single<Boolean> sendPhoneNumber(String phoneNumber);
}
