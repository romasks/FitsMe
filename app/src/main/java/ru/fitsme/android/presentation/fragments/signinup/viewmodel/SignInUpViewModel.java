package ru.fitsme.android.presentation.fragments.signinup.viewmodel;

import org.jetbrains.annotations.NotNull;

import ru.fitsme.android.domain.entities.signinup.AutoSignInInfo;
import ru.fitsme.android.domain.interactors.auth.ISignInUpInteractor;
import ru.fitsme.android.presentation.fragments.base.BaseViewModel;
import timber.log.Timber;

public class SignInUpViewModel extends BaseViewModel {

    private ISignInUpInteractor signInUpInteractor;

    public SignInUpViewModel(@NotNull ISignInUpInteractor signInUpInteractor) {
        this.signInUpInteractor = signInUpInteractor;
        inject(this);
    }

    public void init() {
        addDisposable(signInUpInteractor.getAutoSignInInfo()
                .subscribe(this::onAutoSignIn, this::onError));
    }

    private void onAutoSignIn(@NotNull AutoSignInInfo autoSignInInfo) {
        if (autoSignInInfo.isAuto()) {
            onSignIn();
        }
    }

    private void onError(Throwable throwable) {
        Timber.tag(getClass().getName()).e(throwable);
    }

    public void onSignUp() {
        navigation.goSignUp();
    }

    public void onSignIn() {
        navigation.goSignIn();
    }

    @Override
    public void onBackPressed() {
    }
}
