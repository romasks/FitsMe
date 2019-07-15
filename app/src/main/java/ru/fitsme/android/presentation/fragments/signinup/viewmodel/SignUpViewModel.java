package ru.fitsme.android.presentation.fragments.signinup.viewmodel;

import org.jetbrains.annotations.NotNull;

import ru.fitsme.android.domain.entities.auth.SignInUpResult;
import ru.fitsme.android.domain.interactors.auth.IAuthInteractor;
import ru.fitsme.android.domain.interactors.auth.ISignInteractor;
import ru.fitsme.android.presentation.common.livedata.NonNullLiveData;
import ru.fitsme.android.presentation.common.livedata.NonNullMutableLiveData;
import ru.fitsme.android.presentation.fragments.base.BaseViewModel;
import ru.fitsme.android.presentation.fragments.signinup.entities.SignInUpState;
import timber.log.Timber;

public class SignUpViewModel extends BaseViewModel {

    private ISignInteractor signInteractor;

    private NonNullMutableLiveData<SignInUpState> fieldsStateLiveData = new NonNullMutableLiveData<>();

    public SignUpViewModel(ISignInteractor signInteractor) {
        this.signInteractor = signInteractor;
        inject(this);
    }

    public void init() {

    }

    public void onSignUp(String login, String password) {
        fieldsStateLiveData.setValue(new SignInUpState(null, true));
        addDisposable(signInteractor.signUp(login, password)
                .subscribe(this::onSignInUpResult, this::onError));
    }

    private void onSignInUpResult(@NotNull SignInUpResult signInUpResult) {
        fieldsStateLiveData.setValue(new SignInUpState(signInUpResult, false));
        if (signInUpResult.isSuccess()) {
            navigation.goToMainItem();
        }
    }

    private void onError(Throwable throwable) {
        Timber.tag(getClass().getName()).e(throwable);
    }

    public NonNullLiveData<SignInUpState> getFieldsStateLiveData() {
        return fieldsStateLiveData;
    }
}
