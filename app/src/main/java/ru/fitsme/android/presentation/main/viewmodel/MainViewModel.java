package ru.fitsme.android.presentation.main.viewmodel;

import android.arch.lifecycle.ViewModel;
import android.databinding.ObservableBoolean;

public class MainViewModel extends ViewModel {
    public static ObservableBoolean isOnline = new ObservableBoolean(true);
}
