package ru.fitsme.android.presentation.fragments.profile.viewmodel;

import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;

import ru.fitsme.android.domain.interactors.profile.IProfileInteractor;
import ru.fitsme.android.presentation.fragments.base.BaseViewModel;
import ru.fitsme.android.presentation.fragments.main.MainNavigation;

@SuppressWarnings("Injectable")
public class MainProfileViewModel extends BaseViewModel {

    private final IProfileInteractor profileInteractor;

    public MainProfileViewModel(@NotNull IProfileInteractor profileInteractor) {
        this.profileInteractor = profileInteractor;
        inject(this);
    }

    public void init() {

    }

    public void logout(){

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

    @Override
    public void onBackPressed() {
        navigation.finish();
    }
}
