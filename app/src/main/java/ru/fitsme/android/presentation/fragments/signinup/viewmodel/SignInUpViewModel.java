package ru.fitsme.android.presentation.fragments.signinup.viewmodel;

import org.jetbrains.annotations.NotNull;

import ru.fitsme.android.domain.interactors.auth.IAuthInteractor;
import ru.fitsme.android.presentation.fragments.base.BaseViewModel;

public class SignInUpViewModel extends BaseViewModel {

    private IAuthInteractor authInteractor;

    public SignInUpViewModel(@NotNull IAuthInteractor authInteractor) {
        this.authInteractor = authInteractor;
        inject(this);
    }

    public void init() {
    }

    public void onSignUp() {
        navigation.goSignUp();
    }

    public void onSignIn() {
        navigation.goSignIn();
    }
}
