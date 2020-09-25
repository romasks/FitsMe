package ru.fitsme.android.presentation.fragments.auth;

import javax.inject.Inject;

import androidx.databinding.ObservableField;
import io.reactivex.disposables.Disposable;
import ru.fitsme.android.domain.entities.exceptions.user.UserException;
import ru.fitsme.android.domain.interactors.auth.ISignInteractor;
import ru.fitsme.android.presentation.fragments.base.BaseViewModel;
import ru.fitsme.android.presentation.main.AuthNavigation;

public class NumberViewModel extends BaseViewModel {

    @Inject
    AuthNavigation authNavigation;

    @Inject
    ISignInteractor interactor;

    public ObservableField<String> message = new ObservableField<String>();
    private boolean numberSendingInProgress = false;

    public NumberViewModel() {
        inject(this);
    }

    @Override
    public void onBackPressed() {
        authNavigation.finish();
    }

    public void sendPhoneNumber(String phoneNumber) {
        if (!numberSendingInProgress) {
            Disposable disposable = interactor.sendPhoneNumber(phoneNumber)
                    .subscribe(codeResponse -> {
                        numberSendingInProgress = false;
                        if (codeResponse.getStatus().equals("confirmation code sent")) {
                            authNavigation.goToCodeInput();
                        }
                    }, error -> {
                        numberSendingInProgress = false;
                        if (error instanceof UserException) {
                            UserException userException = (UserException) error;
                            message.set(userException.getMessage());
                        }
                    });
            addDisposable(disposable);
        }
        numberSendingInProgress = true;
    }

    public void goToAgreement() {
        authNavigation.goToAgreement();
    }
}
