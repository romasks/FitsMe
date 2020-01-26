package ru.fitsme.android.presentation.fragments.auth;

import androidx.databinding.ObservableField;
import androidx.lifecycle.MutableLiveData;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import io.reactivex.internal.operators.observable.ObservableFilter;
import ru.fitsme.android.R;
import ru.fitsme.android.app.App;
import ru.fitsme.android.domain.entities.exceptions.user.UserException;
import ru.fitsme.android.domain.interactors.auth.ISignInteractor;
import ru.fitsme.android.presentation.fragments.base.BaseViewModel;
import ru.fitsme.android.presentation.main.AuthNavigation;
import timber.log.Timber;

public class NumberViewModel extends BaseViewModel {

    @Inject
    AuthNavigation authNavigation;
    @Inject
    ISignInteractor interactor;

    public ObservableField<String> message = new ObservableField<String>();

    public NumberViewModel(){
        inject(this);   
    }
    
    @Override
    public void onBackPressed() {
        
    }

    public void init() {
        
    }

    public void sendPhoneNumber(String phoneNumber) {
        Disposable disposable = interactor.sendPhoneNumber(phoneNumber)
                .subscribe(codeResponse -> {
                    if (codeResponse.getStatus().equals("confirmation code sent")){
                        authNavigation.goToCodeInput();
                    }
                }, error -> {
                    if (error instanceof UserException) {
                        UserException userException = (UserException) error;
                        message.set(userException.getMessage());
                    }
                });
        addDisposable(disposable);
    }
}
