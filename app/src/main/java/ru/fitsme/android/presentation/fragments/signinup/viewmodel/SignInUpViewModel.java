package ru.fitsme.android.presentation.fragments.signinup.viewmodel;

import android.arch.lifecycle.ViewModel;

import javax.inject.Inject;

import ru.fitsme.android.app.App;
import ru.fitsme.android.app.Navigation;

public class SignInUpViewModel extends ViewModel {

    @Inject
    Navigation navigation;

    public SignInUpViewModel() {
        App.getInstance().getDi().inject(this);
    }

    public void onSignUp() {
        navigation.goSignUp();
    }

    public void onSignIn() {
        navigation.goSignIn();
    }
}
