package ru.fitsme.android.presentation.fragments.profile;

import org.jetbrains.annotations.NotNull;

import ru.fitsme.android.domain.interactors.profile.IProfileInteractor;
import ru.fitsme.android.presentation.fragments.base.BaseViewModel;

public class ProfileViewModel extends BaseViewModel {

    private final IProfileInteractor profileInteractor;

    public ProfileViewModel(@NotNull IProfileInteractor profileInteractor) {
        this.profileInteractor = profileInteractor;
    }

    public void init() {

    }

    void logout(){

    }
}
