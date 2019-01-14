package ru.fitsme.android.presentation.fragments.signinup.viewmodel;

import android.arch.lifecycle.ViewModel;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import ru.fitsme.android.app.App;
import ru.fitsme.android.app.Navigation;
import ru.fitsme.android.domain.entities.SignInInfo;
import ru.fitsme.android.domain.interactors.auth.ISignInUpInteractor;
import ru.fitsme.android.presentation.common.livedata.NonNullLiveData;
import ru.fitsme.android.presentation.common.livedata.NonNullMutableLiveData;
import ru.fitsme.android.presentation.fragments.signinup.entities.FieldsState;
import ru.fitsme.android.presentation.fragments.signinup.entities.FieldsStateBuilder;

import static ru.fitsme.android.presentation.main.MainViewModel.NAV_DEBUG;

public class SignUpViewModel extends ViewModel {

    @Inject
    ISignInUpInteractor signInUpInteractor;

    @Inject
    Navigation navigation;

    private Disposable disposable;
    private NonNullMutableLiveData<FieldsState> fieldsStateLiveData = new NonNullMutableLiveData<>();

    public SignUpViewModel() {
        App.getInstance().getDi().inject(this);
    }

    public void onSignUp(String login, String password) {
        FieldsStateBuilder builder = new FieldsStateBuilder();
        if (!signInUpInteractor.checkLogin(login)) {
            builder.setLoginError("Login incorrect");
        }
        if (!signInUpInteractor.checkPassword(password)) {
            builder.setPasswordError("Password incorrect");
        }
        if (builder.isClear()) {
            builder.setLoading(true);
            disposable = signInUpInteractor.register(new SignInInfo(login, password))
                    .subscribe(() -> {
                        fieldsStateLiveData.setValue(new FieldsStateBuilder().setLoading(false)
                                .complete());
                        navigation.getRouter().newRootScreen(NAV_DEBUG);
                    }, throwable -> {
                        fieldsStateLiveData.setValue(new FieldsStateBuilder().setLoading(false)
                                .setCommonError(throwable.getMessage())
                                .complete());
                    });
        }
        fieldsStateLiveData.setValue(builder.complete());
    }

    public NonNullLiveData<FieldsState> getFieldsStateLiveData() {
        return fieldsStateLiveData;
    }

    @Override
    protected void onCleared() {
        super.onCleared();

        if (disposable != null) {
            disposable.dispose();
        }
    }
}
