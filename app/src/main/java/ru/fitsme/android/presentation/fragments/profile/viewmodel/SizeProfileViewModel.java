package ru.fitsme.android.presentation.fragments.profile.viewmodel;

import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;

import ru.fitsme.android.domain.interactors.profile.IProfileInteractor;
import ru.fitsme.android.presentation.fragments.base.BaseViewModel;
import ru.fitsme.android.presentation.fragments.main.MainNavigation;

public class SizeProfileViewModel extends BaseViewModel {

    @Inject
    MainNavigation navigation;
    private final IProfileInteractor profileInteractor;

    public SizeProfileViewModel(@NotNull IProfileInteractor profileInteractor) {
        this.profileInteractor = profileInteractor;
        inject(this);
    }

    public void init() {
    }

    public void goBack() {
        navigation.goToMainProfile();
    }
}
