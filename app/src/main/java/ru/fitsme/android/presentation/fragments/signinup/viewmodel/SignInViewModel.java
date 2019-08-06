package ru.fitsme.android.presentation.fragments.signinup.viewmodel;

import org.jetbrains.annotations.NotNull;

import ru.fitsme.android.domain.entities.auth.SignInUpResult;
import ru.fitsme.android.domain.interactors.auth.ISignInteractor;
import ru.fitsme.android.presentation.common.livedata.NonNullLiveData;
import ru.fitsme.android.presentation.common.livedata.NonNullMutableLiveData;
import ru.fitsme.android.presentation.fragments.base.BaseViewModel;
import ru.fitsme.android.presentation.fragments.signinup.entities.SignInUpState;
import timber.log.Timber;

public class SignInViewModel extends BaseViewModel {

    private ISignInteractor signInteractor;

    private NonNullMutableLiveData<SignInUpState> fieldsStateLiveData = new NonNullMutableLiveData<>();

    public SignInViewModel(@NotNull ISignInteractor signInteractor) {
        this.signInteractor = signInteractor;
        inject(this);
    }

    public void init(){}

    public void onSignIn(String login, String password) {
        startLoading();
        addDisposable(signInteractor.signIn(login, password)
                .subscribe(this::onSignInResult, this::onError));
    }

    private void onSignInResult(SignInUpResult signInUpResult) {
        stopLoading(signInUpResult);
        if (signInUpResult.isSuccess()) {
            authNavigation.goToMainItem();
        }
    }

    private void onError(Throwable throwable) {
        Timber.tag(getClass().getName()).e(throwable);
    }

    private void startLoading() {
        fieldsStateLiveData.setValue(new SignInUpState(null, true));
    }

    private void stopLoading(SignInUpResult signInUpResult) {
        fieldsStateLiveData.setValue(new SignInUpState(signInUpResult, false));
    }

    public NonNullLiveData<SignInUpState> getFieldsStateLiveData() {
        return fieldsStateLiveData;
    }
}
