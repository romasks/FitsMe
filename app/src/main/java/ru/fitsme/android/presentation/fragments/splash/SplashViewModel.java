package ru.fitsme.android.presentation.fragments.splash;

import android.support.annotation.NonNull;

import ru.fitsme.android.domain.entities.auth.AuthInfo;
import ru.fitsme.android.domain.interactors.auth.IAuthInteractor;
import ru.fitsme.android.presentation.fragments.base.BaseViewModel;
import timber.log.Timber;

public class SplashViewModel extends BaseViewModel {

    private final IAuthInteractor authInteractor;

    public SplashViewModel(@NonNull IAuthInteractor authInteractor){
        this.authInteractor = authInteractor;
        inject(this);
    }

    public void init() {
                    addDisposable(authInteractor.getAuthInfo()
                            .subscribe(this ::onAuthInfoGotten, this::onError));
    }

    private void onAuthInfoGotten(@NonNull AuthInfo authInfo) {
        if (authInfo.isAuth()){
            authNavigation.goToMainItem();
        } else {
            authNavigation.goSignInUp();
        }
    }

    private void onError(Throwable throwable) {
        Timber.e(throwable);
    }
}
