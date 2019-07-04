package ru.fitsme.android.presentation.fragments.signinup.viewmodel;

import org.jetbrains.annotations.NotNull;

import ru.fitsme.android.domain.entities.signinup.AutoSignInInfo;
import ru.fitsme.android.domain.entities.signinup.SignInUpResult;
import ru.fitsme.android.domain.interactors.auth.ISignInUpInteractor;
import ru.fitsme.android.presentation.common.livedata.NonNullLiveData;
import ru.fitsme.android.presentation.common.livedata.NonNullMutableLiveData;
import ru.fitsme.android.presentation.fragments.base.BaseViewModel;
import ru.fitsme.android.presentation.fragments.signinup.entities.SignInUpState;
import timber.log.Timber;

public class SignInViewModel extends BaseViewModel {

    private ISignInUpInteractor signInUpInteractor;

    private NonNullMutableLiveData<SignInUpState> fieldsStateLiveData = new NonNullMutableLiveData<>();

    public SignInViewModel(@NotNull ISignInUpInteractor signInUpInteractor) {
        this.signInUpInteractor = signInUpInteractor;
        inject(this);
    }

    public void init(){}

//    public void init() {
//        addDisposable(signInUpInteractor.getAutoSignInInfo()
//                .subscribe(this::onAutoSignIn, this::onError));
//    }
//
//    private void onAutoSignIn(@NotNull AutoSignInInfo autoSignInInfo) {
//        if (autoSignInInfo.getSignInInfo() != null && autoSignInInfo.isAuto()) {
//            startLoading();
//            addDisposable(signInUpInteractor.authorize(autoSignInInfo.getSignInInfo())
//                    .subscribe(this::onSignInResult, this::onError));
//        }
//    }
//
    private void onSignInResult(SignInUpResult signInUpResult) {
        stopLoading(signInUpResult);
        if (signInUpResult.isSuccess()) {
            navigation.goToMainItem();
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

    public void onSignIn(String login, String password) {
        startLoading();
        addDisposable(signInUpInteractor.authorize(login, password)
                .subscribe(this::onSignInResult, this::onError));
    }

    public NonNullLiveData<SignInUpState> getFieldsStateLiveData() {
        return fieldsStateLiveData;
    }
}
