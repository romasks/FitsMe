package ru.fitsme.android.presentation.fragments.profile.viewmodel;

import android.app.Activity;

import javax.inject.Inject;

import ru.fitsme.android.app.App;
import ru.fitsme.android.domain.interactors.profile.IProfileInteractor;
import ru.fitsme.android.presentation.fragments.base.BaseViewModel;
import ru.fitsme.android.presentation.main.view.MainActivity;

@SuppressWarnings("Injectable")
public class MainProfileViewModel extends BaseViewModel {

    @Inject
    IProfileInteractor profileInteractor;

    public MainProfileViewModel() {
        inject(this);
    }

    public void init() {

    }

    public void logout(Activity activity) {
        App.getInstance().getAuthInfo().clearToken();
        ((MainActivity) activity).logout();
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

    public void onViewCreated() {
        profileInteractor.updateInfo();
    }

    @Override
    public void onBackPressed() {
        navigation.finish();
    }
}
