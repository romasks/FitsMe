package ru.fitsme.android.presentation.fragments.profile.viewmodel;

import org.jetbrains.annotations.NotNull;

import ru.fitsme.android.domain.interactors.profile.IProfileInteractor;
import ru.fitsme.android.presentation.fragments.base.BaseViewModel;

public class MainProfileViewModel extends BaseViewModel {

    private final IProfileInteractor profileInteractor;

    public MainProfileViewModel(@NotNull IProfileInteractor profileInteractor) {
        this.profileInteractor = profileInteractor;
    }

    public void init() {

    }

    public void logout(){

    }
}
