package ru.fitsme.android.presentation.main;

import android.arch.lifecycle.ViewModel;

import ru.fitsme.android.presentation.common.livedata.NonNullLiveData;
import ru.fitsme.android.presentation.common.livedata.NonNullMutableLiveData;

public class MainViewModel extends ViewModel {

    public static final int NAV_AUTH_REGISTER = 0;
    public static final int NAV_AUTH = 1;
    public static final int NAV_REGISTER = 2;
    public static final int NAV_DEBUG = 3;

    private NonNullMutableLiveData<Integer> navigationLiveData = new NonNullMutableLiveData<>();

    public MainViewModel() {
        navigationLiveData.setValue(NAV_AUTH_REGISTER);
    }

    public NonNullLiveData<Integer> getNavigationLiveData() {
        return navigationLiveData;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
    }
}
