package ru.fitsme.android.presentation.fragments.auth;

import javax.inject.Inject;

import ru.fitsme.android.presentation.fragments.base.BaseViewModel;
import ru.fitsme.android.presentation.main.AuthNavigation;

public class NumberViewModel extends BaseViewModel {

    @Inject
    AuthNavigation authNavigation;
    
    NumberViewModel(){
        inject(this);   
    }
    
    @Override
    public void onBackPressed() {
        
    }

    public void init() {
        
    }

    public void getAuthCode(String s) {

    }
}
