package ru.fitsme.android.presentation.fragments.auth;

import android.annotation.SuppressLint;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import javax.inject.Inject;

import ru.fitsme.android.domain.interactors.auth.ISignInteractor;
import ru.fitsme.android.presentation.fragments.base.BaseViewModel;
import ru.fitsme.android.presentation.main.AuthNavigation;

public class CodeViewModel extends BaseViewModel {

    @Inject
    AuthNavigation authNavigation;

    @Inject
    ISignInteractor interactor;

    private MutableLiveData<Boolean> isCodeVerified = new MutableLiveData<>(true);

    public CodeViewModel() {
        inject(this);
    }

    LiveData<Boolean> isCodeVerified() {
        return isCodeVerified;
    }

    @SuppressLint("CheckResult")
    public void verifyCode(String code) {
        interactor.verifyCode(code).subscribe(authInfo -> {
            if (authInfo.getToken() != null) {
                receivedSuccess();
            } else {
                receivedError();
            }
        }, error -> receivedError());

    }

    private void receivedSuccess() {
        isCodeVerified.setValue(true);
        authNavigation.goToMainItem();
    }

    private void receivedError() {
        isCodeVerified.setValue(false);
    }

    @Override
    public void onBackPressed() {
        authNavigation.goBack();
    }
}
