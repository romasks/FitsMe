package ru.fitsme.android.presentation.fragments.signinup.entities;

import ru.fitsme.android.data.entities.response.signinup.SignInUpResult;

public class SignInUpState {
    private SignInUpResult signInUpResult;
    private boolean loading;

    public SignInUpState(SignInUpResult signInUpResult, boolean loading) {
        this.signInUpResult = signInUpResult;
        this.loading = loading;
    }

    public SignInUpResult getSignInUpResult() {
        return signInUpResult;
    }

    public boolean isLoading() {
        return loading;
    }
}
