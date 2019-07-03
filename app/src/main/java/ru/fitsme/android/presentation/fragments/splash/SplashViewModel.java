package ru.fitsme.android.presentation.fragments.splash;

import android.support.annotation.NonNull;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.TimeUnit;

import ru.fitsme.android.domain.entities.signinup.AutoSignInInfo;
import ru.fitsme.android.domain.entities.signinup.SignInUpResult;
import ru.fitsme.android.domain.interactors.auth.ISignInUpInteractor;
import ru.fitsme.android.presentation.fragments.base.BaseViewModel;
import ru.fitsme.android.presentation.fragments.signinup.entities.SignInUpState;
import timber.log.Timber;

public class SplashViewModel extends BaseViewModel {

    private final ISignInUpInteractor signInUpInteractor;

    public SplashViewModel(@NonNull ISignInUpInteractor signInUpInteractor){
        this.signInUpInteractor = signInUpInteractor;
        inject(this);
    }

    public void init() {
//        try {
            addDisposable(signInUpInteractor.getAutoSignInInfo()
                    .subscribe(this::onAutoSignIn, this::onError));
//            TimeUnit.SECONDS.sleep(1);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
    }

    private void onAutoSignIn(@NotNull AutoSignInInfo autoSignInInfo) {
        if (autoSignInInfo.getSignInInfo() != null && autoSignInInfo.isAuto()) {
            addDisposable(signInUpInteractor.authorize(autoSignInInfo.getSignInInfo())
                    .subscribe(this::onSignInResult, this::onError));
        } else {
            navigation.goSignInUp();
        }
    }

    private void onSignInResult(SignInUpResult signInUpResult) {
        if (signInUpResult.isSuccess()) {
            navigation.goToMainItem();
        } else {
            navigation.goSignInUp();
        }
    }

    private void onError(Throwable throwable) {
        Timber.tag(getClass().getName()).e(throwable);
    }

}
