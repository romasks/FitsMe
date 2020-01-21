package ru.fitsme.android.presentation.fragments.splash;

import androidx.annotation.NonNull;

import javax.inject.Inject;

import ru.fitsme.android.domain.entities.auth.AuthInfo;
import ru.fitsme.android.domain.interactors.auth.IAuthInteractor;
import ru.fitsme.android.presentation.fragments.base.BaseViewModel;
import ru.fitsme.android.presentation.main.AuthNavigation;
import timber.log.Timber;

@SuppressWarnings("Injectable")
public class SplashViewModel extends BaseViewModel {

    @Inject
    AuthNavigation authNavigation;
    private final IAuthInteractor authInteractor;

    public SplashViewModel(@NonNull IAuthInteractor authInteractor) {
        this.authInteractor = authInteractor;
        inject(this);
    }

    public void init() {
        addDisposable(authInteractor.getAuthInfo()
                .subscribe(this::onAuthInfoGotten, this::onError));
    }

    private void onAuthInfoGotten(@NonNull AuthInfo authInfo) {
        if (authInfo.isAuth()) {
            authNavigation.goToMainItem();
        } else {
            authNavigation.goToAuth();
        }
    }

    private void onError(Throwable throwable) {
        Timber.e(throwable);
    }

    @Override
    public void onBackPressed() {
        //do nothing
    }
}
