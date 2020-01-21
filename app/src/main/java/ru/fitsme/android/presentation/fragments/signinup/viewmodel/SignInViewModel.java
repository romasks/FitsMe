package ru.fitsme.android.presentation.fragments.signinup.viewmodel;

import javax.inject.Inject;

import ru.fitsme.android.domain.entities.auth.SignInUpResult;
import ru.fitsme.android.domain.interactors.auth.ISignInteractor;
import ru.fitsme.android.presentation.common.livedata.NonNullLiveData;
import ru.fitsme.android.presentation.common.livedata.NonNullMutableLiveData;
import ru.fitsme.android.presentation.fragments.base.BaseViewModel;
import ru.fitsme.android.presentation.fragments.signinup.entities.SignInUpState;
import ru.fitsme.android.presentation.main.AuthNavigation;
import timber.log.Timber;

@SuppressWarnings("Injectable")
public class SignInViewModel extends BaseViewModel {

    @Inject
    AuthNavigation authNavigation;

    @Inject
    ISignInteractor signInteractor;

    private NonNullMutableLiveData<SignInUpState> fieldsStateLiveData = new NonNullMutableLiveData<>();

    public SignInViewModel() {
        inject(this);
    }

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
