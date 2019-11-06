package ru.fitsme.android.presentation.main.viewmodel;

import androidx.databinding.ObservableBoolean;
import androidx.lifecycle.ViewModel;

public class MainViewModel extends ViewModel {
    public static ObservableBoolean isOnline = new ObservableBoolean(true);
}
