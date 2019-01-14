package ru.fitsme.android.domain.interactors.auth;

import android.support.annotation.NonNull;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Completable;
import ru.fitsme.android.domain.entities.AuthInfo;
import ru.fitsme.android.domain.entities.SignInInfo;
import timber.log.Timber;

@Singleton
public class SignInUpInteractor implements ISignInUpInteractor {
    private ISignInUpRepository signInRepository;
    private IUserInfoRepository userInfoRepository;
    private ITextValidator textValidator;

    @Inject
    public SignInUpInteractor(ISignInUpRepository signInRepository,
                              IUserInfoRepository userInfoRepository,
                              ITextValidator textValidator) {
        this.signInRepository = signInRepository;
        this.userInfoRepository = userInfoRepository;
        this.textValidator = textValidator;
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

    @Override
    public boolean checkLogin(String login) {
        return textValidator.checkLogin(login);
    }

    @Override
    public boolean checkPassword(String password) {
        return textValidator.checkPassword(password);
    }
}
