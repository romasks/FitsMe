package ru.fitsme.android.presentation.fragments.auth;

import javax.inject.Inject;

import ru.fitsme.android.domain.interactors.auth.IAuthInteractor;
import ru.fitsme.android.presentation.fragments.base.BaseViewModel;
import ru.fitsme.android.presentation.main.AuthNavigation;

public class NumberViewModel extends BaseViewModel {

    @Inject
    AuthNavigation authNavigation;
    @Inject
    IAuthInteractor interactor;

    public NumberViewModel(){
        inject(this);   
    }
    
    @Override
    public void onBackPressed() {
        
    }

    public void init() {
        
    }

    public void sendPhoneNumber(String phoneNumber) {
        interactor.sendPhoneNumber(phoneNumber)
                .subscribe(isSent -> {
                    if (isSent){
                        authNavigation.goToCodeInput();
                    }
                });
    }
}
