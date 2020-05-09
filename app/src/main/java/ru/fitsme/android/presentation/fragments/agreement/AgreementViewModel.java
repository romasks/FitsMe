package ru.fitsme.android.presentation.fragments.agreement;

import javax.inject.Inject;

import ru.fitsme.android.presentation.fragments.base.BaseViewModel;
import ru.fitsme.android.presentation.main.AuthNavigation;

public class AgreementViewModel extends BaseViewModel {

    @Inject
    AuthNavigation authNavigation;

    public AgreementViewModel() {
        inject(this);
    }

    @Override
    public void onBackPressed() {
        authNavigation.goBack();
    }
}
