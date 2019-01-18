package ru.fitsme.android.presentation.fragments.signinup.viewmodel;

import android.arch.lifecycle.ViewModel;

import javax.inject.Inject;

import ru.fitsme.android.app.App;
import ru.fitsme.android.app.Navigation;

import static ru.fitsme.android.presentation.main.viewmodel.MainViewModel.NAV_SIGN_IN;
import static ru.fitsme.android.presentation.main.viewmodel.MainViewModel.NAV_SIGN_UP;

public class SignInUpViewModel extends ViewModel {

    @Inject
    Navigation navigation;

    public SignInUpViewModel() {
        App.getInstance().getDi().inject(this);
    }

    public void onSignUp() {
        navigation.getRouter().navigateTo(NAV_SIGN_UP);
    }

    public void onSignIn() {
        navigation.getRouter().navigateTo(NAV_SIGN_IN);
    }
}
