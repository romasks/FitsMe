package ru.fitsme.android.presentation.fragments.signinup.viewmodel;

import javax.inject.Inject;

import ru.fitsme.android.presentation.fragments.base.BaseViewModel;
import ru.fitsme.android.presentation.main.AuthNavigation;

@SuppressWarnings("Injectable")
public class SignInUpViewModel extends BaseViewModel {

    @Inject
    AuthNavigation authNavigation;

    public SignInUpViewModel() {
        inject(this);
    }

    public void onSignUp() {
        authNavigation.goSignUp();
    }

    public void onSignIn() {
        authNavigation.goSignIn();
    }
}
