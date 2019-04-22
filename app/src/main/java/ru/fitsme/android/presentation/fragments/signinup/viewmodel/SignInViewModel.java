package ru.fitsme.android.presentation.fragments.signinup.viewmodel;

import android.arch.lifecycle.ViewModel;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import ru.fitsme.android.app.App;
import ru.fitsme.android.app.Navigation;
import ru.fitsme.android.data.entities.response.signinup.SignInUpResult;
import ru.fitsme.android.domain.interactors.auth.ISignInUpInteractor;
import ru.fitsme.android.presentation.common.livedata.NonNullLiveData;
import ru.fitsme.android.presentation.common.livedata.NonNullMutableLiveData;
import ru.fitsme.android.presentation.fragments.signinup.entities.SignInUpState;

public class SignInViewModel extends ViewModel {

    @Inject
    ISignInUpInteractor signInUpInteractor;

    @Inject
    Navigation navigation;

    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private NonNullMutableLiveData<SignInUpState> fieldsStateLiveData = new NonNullMutableLiveData<>();

    public SignInViewModel() {
        App.getInstance().getDi().inject(this);

        compositeDisposable.add(signInUpInteractor.getAutoSignInInfo()
                .subscribe(autoSignInInfo -> {
                    if (autoSignInInfo.getSignInInfo() != null) {
                        if (autoSignInInfo.isAuto()) {
                            startLoading();
                            compositeDisposable.add(signInUpInteractor.authorize(autoSignInInfo.getSignInInfo())
                                    .subscribe(this::onSignInResult));
                        }
                    }
                }));
    }

    private void startLoading() {
        fieldsStateLiveData.setValue(new SignInUpState(null, true));
    }

    public void onSignIn(String login, String password) {
        startLoading();
        compositeDisposable.add(signInUpInteractor.authorize(login, password)
                .subscribe(this::onSignInResult));
    }

    private void onSignInResult(SignInUpResult signInUpResult) {
        stopLoading(signInUpResult);
        if (signInUpResult.isSuccess()) {
            navigation.goToMainItem();
        }
    }

    private void stopLoading(SignInUpResult signInUpResult) {
        fieldsStateLiveData.setValue(new SignInUpState(signInUpResult, false));
    }

    public NonNullLiveData<SignInUpState> getFieldsStateLiveData() {
        return fieldsStateLiveData;
    }

    @Override
    protected void onCleared() {
        super.onCleared();

        compositeDisposable.dispose();
    }
}
