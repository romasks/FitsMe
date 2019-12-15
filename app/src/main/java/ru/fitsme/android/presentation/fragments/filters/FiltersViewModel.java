package ru.fitsme.android.presentation.fragments.filters;

import ru.fitsme.android.domain.interactors.clothes.IClothesInteractor;
import ru.fitsme.android.presentation.fragments.base.BaseViewModel;

public class FiltersViewModel extends BaseViewModel {

    public FiltersViewModel(IClothesInteractor interactor){
        inject(this);
    }

    @Override
    public void onBackPressed() {
        navigation.goBack();
    }

    public void init() {

    }
}
