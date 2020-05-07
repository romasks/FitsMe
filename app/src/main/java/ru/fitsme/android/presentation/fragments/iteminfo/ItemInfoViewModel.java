package ru.fitsme.android.presentation.fragments.iteminfo;

import androidx.lifecycle.LiveData;

import javax.inject.Inject;

import ru.fitsme.android.domain.interactors.profile.IProfileInteractor;
import ru.fitsme.android.presentation.fragments.base.BaseViewModel;

public class ItemInfoViewModel extends BaseViewModel {

    @Inject
    IProfileInteractor profileInteractor;

    public ItemInfoViewModel() {
        inject(this);
    }


    public void init() {
        profileInteractor.updateInfo();
    }

    public LiveData<String> getCurrentTopSize(){
        return profileInteractor.getCurrentTopSize();
    }

    public LiveData<String> getCurrentBottomSize(){
        return profileInteractor.getCurrentBottomSize();
    }
}
