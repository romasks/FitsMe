package ru.fitsme.android.presentation.fragments.profile.viewmodel;

import android.app.Activity;

import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;

import ru.fitsme.android.app.App;
import ru.fitsme.android.domain.interactors.profile.IProfileInteractor;
import ru.fitsme.android.presentation.fragments.base.BaseViewModel;
import ru.fitsme.android.presentation.fragments.main.MainNavigation;
import ru.fitsme.android.presentation.main.view.MainActivity;

@SuppressWarnings("Injectable")
public class MainProfileViewModel extends BaseViewModel {

    @Inject
    MainNavigation navigation;

    private final IProfileInteractor profileInteractor;

    public MainProfileViewModel(@NotNull IProfileInteractor profileInteractor) {
        this.profileInteractor = profileInteractor;
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

    public void goToOrdersReturn() {
        navigation.goToOrdersReturn();
    }

    public void onViewCreated() {
        profileInteractor.updateInfo();
    }
}
