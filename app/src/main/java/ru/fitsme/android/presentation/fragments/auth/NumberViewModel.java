package ru.fitsme.android.presentation.fragments.auth;

import javax.inject.Inject;

import ru.fitsme.android.presentation.fragments.base.BaseViewModel;
import ru.fitsme.android.presentation.main.AuthNavigation;
import timber.log.Timber;

public class NumberViewModel extends BaseViewModel {

    @Inject
    AuthNavigation authNavigation;
    
    public NumberViewModel(){
        inject(this);   
    }
    
    @Override
    public void onBackPressed() {
        
    }

    public void init() {
        
    }

    public void getAuthCode(String phoneNumber) {
        Timber.d(phoneNumber);
    }
}
