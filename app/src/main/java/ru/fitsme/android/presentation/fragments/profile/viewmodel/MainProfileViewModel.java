package ru.fitsme.android.presentation.fragments.profile.viewmodel;

import javax.inject.Inject;

import ru.fitsme.android.domain.interactors.auth.IAuthInteractor;
import ru.fitsme.android.domain.interactors.profile.IProfileInteractor;
import ru.fitsme.android.presentation.fragments.base.BaseViewModel;
import ru.fitsme.android.presentation.main.AuthNavigation;

@SuppressWarnings("Injectable")
public class MainProfileViewModel extends BaseViewModel {

    @Inject
    AuthNavigation authNavigation;

    @Inject
    IAuthInteractor authInteractor;

    @Inject
    IProfileInteractor profileInteractor;

    public MainProfileViewModel() {
        inject(this);
    }

    public void logout() {
        authInteractor.clearAuthInfo();
        authNavigation.goToAuth();
    }

    public void goToSizeProfile() {
        navigation.goToSizeProfile();
    }

    public void goToOrderHistoryProfile() {
        navigation.goToOrderHistoryProfile();
    }

    public void goToOrdersReturn() {
        navigation.goToOrdersReturn();
    }

    public void goToLeaveFeedback() {
        navigation.goToLeaveFeedback();
    }

    public void onViewCreated() {
        profileInteractor.updateInfo();
    }

    @Override
    public void onBackPressed() {
        navigation.finish();
    }
}
