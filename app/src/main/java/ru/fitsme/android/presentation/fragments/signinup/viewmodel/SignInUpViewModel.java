package ru.fitsme.android.presentation.fragments.signinup.viewmodel;

import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;

import ru.fitsme.android.domain.interactors.auth.IAuthInteractor;
import ru.fitsme.android.presentation.fragments.base.BaseViewModel;
import ru.fitsme.android.presentation.main.AuthNavigation;

@SuppressWarnings("Injectable")
public class SignInUpViewModel extends BaseViewModel {

    @Inject
    AuthNavigation authNavigation;
    private IAuthInteractor authInteractor;

    public SignInUpViewModel(@NotNull IAuthInteractor authInteractor) {
        this.authInteractor = authInteractor;
        inject(this);
    }

    public void init() {
    }

    public void onSignUp() {
        authNavigation.goSignUp();
    }

    public void onSignIn() {
        authNavigation.goSignIn();
    }
}
