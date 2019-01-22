package ru.fitsme.android.presentation.fragments.signinup.viewmodel;

import android.arch.lifecycle.ViewModel;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import ru.fitsme.android.app.App;
import ru.fitsme.android.app.Navigation;
import ru.fitsme.android.domain.interactors.auth.ISignInUpInteractor;

public class SignInUpViewModel extends ViewModel {

    private final Disposable disposable;

    @Inject
    Navigation navigation;

    @Inject
    ISignInUpInteractor signInUpInteractor;

    public SignInUpViewModel() {
        App.getInstance().getDi().inject(this);

        disposable = signInUpInteractor.getAutoSignInInfo()
                .subscribe(autoSignInInfo -> {
                    if (autoSignInInfo.isAuto()) {
                        onSignIn();
                    }
                });
    }

    public void onSignUp() {
        navigation.goSignUp();
    }

    public void onSignIn() {
        navigation.goSignIn();
    }

    @Override
    protected void onCleared() {
        super.onCleared();

        disposable.dispose();
    }
}
