package ru.fitsme.android.presentation.fragments.signinup.viewmodel;

import ru.fitsme.android.domain.interactors.auth.ISignInUpInteractor;
import ru.fitsme.android.presentation.common.livedata.NonNullLiveData;
import ru.fitsme.android.presentation.common.livedata.NonNullMutableLiveData;
import ru.fitsme.android.presentation.fragments.base.BaseViewModel;
import ru.fitsme.android.presentation.fragments.signinup.entities.SignInUpState;

public class SignUpViewModel extends BaseViewModel {

    private ISignInUpInteractor signInUpInteractor;

    private NonNullMutableLiveData<SignInUpState> fieldsStateLiveData = new NonNullMutableLiveData<>();

    public SignUpViewModel(ISignInUpInteractor signInUpInteractor) {
        this.signInUpInteractor = signInUpInteractor;
        inject(this);
    }

    public void init() {

    }

    public void onSignUp(String login, String password) {
        fieldsStateLiveData.setValue(new SignInUpState(null, true));
        addDisposable(signInUpInteractor.register(login, password)
                .subscribe(signInUpResult -> {
                    SignInUpState signInUpState = new SignInUpState(signInUpResult, false);
                    fieldsStateLiveData.setValue(signInUpState);
                    if (signInUpResult.isSuccess()) {
                        navigation.goToMainItem();
                    }
                })
        );
    }

    public NonNullLiveData<SignInUpState> getFieldsStateLiveData() {
        return fieldsStateLiveData;
    }
}
