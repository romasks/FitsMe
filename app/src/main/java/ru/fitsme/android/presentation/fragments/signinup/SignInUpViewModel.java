package ru.fitsme.android.presentation.fragments.signinup;

import android.arch.lifecycle.ViewModel;

import ru.fitsme.android.app.App;

import static ru.fitsme.android.presentation.main.MainViewModel.NAV_SIGN_IN;
import static ru.fitsme.android.presentation.main.MainViewModel.NAV_SIGN_UP;

public class SignInUpViewModel extends ViewModel {
    public void onSignUp() {
        App.getInstance().getRouter().navigateTo(NAV_SIGN_UP);
    }

    public void onSignIn() {
        App.getInstance().getRouter().navigateTo(NAV_SIGN_IN);
    }
}
