package ru.fitsme.android.presentation.fragments.auth;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import javax.inject.Inject;

import ru.fitsme.android.domain.interactors.auth.IAuthInteractor;
import ru.fitsme.android.presentation.fragments.base.BaseViewModel;
import ru.fitsme.android.presentation.main.AuthNavigation;

public class CodeViewModel extends BaseViewModel {

    @Inject
    AuthNavigation authNavigation;
    @Inject
    IAuthInteractor interactor;

    private MutableLiveData<Boolean> isCodeVerified = new MutableLiveData<>();

    public CodeViewModel() {
        inject(this);
    }

    LiveData<Boolean> isCodeVerified() {
        return isCodeVerified;
    }

    public void verifyCode(CharSequence str) {
        interactor.verifyCode(str);
    }

    private void receivedSuccess() {
        isCodeVerified.setValue(true);
    }

    private void receivedError() {
        isCodeVerified.setValue(false);
    }
}
