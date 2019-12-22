package ru.fitsme.android.presentation.fragments.filters;

import javax.inject.Inject;

import ru.fitsme.android.domain.interactors.clothes.IClothesInteractor;
import ru.fitsme.android.presentation.fragments.base.BaseViewModel;

public class FiltersViewModel extends BaseViewModel {

    @Inject
    IClothesInteractor clothesInteractor;

    public FiltersViewModel() {
        inject(this);
    }

    @Override
    public void onBackPressed() {
        navigation.goBack();
    }

    @Override
    protected void init() {

    }
}
