package ru.fitsme.android.Domain.Interactors.Auth;

import android.support.annotation.NonNull;

import io.reactivex.Completable;
import ru.fitsme.android.Domain.Entities.AuthInfo;
import ru.fitsme.android.Domain.Entities.SignInInfo;
import timber.log.Timber;

public class SignInUpInteractor implements ISignInUpInteractor {
    private ISignInRepository signInRepository;
    private IUserInfoRepository userInfoRepository;

    public SignInUpInteractor(ISignInRepository signInRepository, IUserInfoRepository userInfoRepository) {
        this.signInRepository = signInRepository;
        this.userInfoRepository = userInfoRepository;
    }

    @Override
    @NonNull
    public Completable register(@NonNull SignInInfo signInInfo) {
        return Completable.create(emitter -> {
            try {
                AuthInfo authInfo = signInRepository.register(signInInfo);
                userInfoRepository.setAuthInfo(authInfo);
                userInfoRepository.setSignInInfo(signInInfo);
                emitter.onComplete();
            } catch (Exception e) {
                Timber.d(e);
                emitter.onError(e);
            }
        });
    }

    @Override
    @NonNull
    public Completable authorize(@NonNull SignInInfo signInInfo) {
        return Completable.create(emitter -> {
            try {
                AuthInfo authInfo = signInRepository.authorize(signInInfo);
                userInfoRepository.setAuthInfo(authInfo);
                userInfoRepository.setSignInInfo(signInInfo);
                emitter.onComplete();
            } catch (Exception e) {
                Timber.d(e);
                emitter.onError(e);
            }
        });
    }

    @Override
    @NonNull
    public Completable authorizeAuto() {
        return Completable.create(emitter -> {
            try {
                SignInInfo signInInfo = userInfoRepository.getSignInInfo();
                AuthInfo authInfo = signInRepository.authorize(signInInfo);
                userInfoRepository.setAuthInfo(authInfo);
                emitter.onComplete();
            } catch (Exception e) {
                Timber.d(e);
                emitter.onError(e);
            }
        });
    }
}
