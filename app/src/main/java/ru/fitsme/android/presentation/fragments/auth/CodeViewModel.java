package ru.fitsme.android.presentation.fragments.auth;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import ru.fitsme.android.presentation.fragments.base.BaseViewModel;

public class CodeViewModel extends BaseViewModel {

    private MutableLiveData<Boolean> isCodeVerified = new MutableLiveData<>();

    public CodeViewModel() {
        inject(this);
    }

    LiveData<Boolean> isCodeVerified() {
        return isCodeVerified;
    }

    public void verifyCode() {
        // TODO: send VerifyCode
        receivedError();
    }

    private void receivedSuccess() {
        isCodeVerified.setValue(true);
    }

    private void receivedError() {
        isCodeVerified.setValue(false);
    }
}
