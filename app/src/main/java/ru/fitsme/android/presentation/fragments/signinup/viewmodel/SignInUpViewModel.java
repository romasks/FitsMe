package ru.fitsme.android.presentation.fragments.signinup.viewmodel;

import org.jetbrains.annotations.NotNull;

import ru.fitsme.android.domain.interactors.auth.IAuthInteractor;
import ru.fitsme.android.presentation.fragments.base.BaseViewModel;

public class SignInUpViewModel extends BaseViewModel {

    private IAuthInteractor signInUpInteractor;

    public SignInUpViewModel(@NotNull IAuthInteractor signInUpInteractor) {
        this.signInUpInteractor = signInUpInteractor;
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
