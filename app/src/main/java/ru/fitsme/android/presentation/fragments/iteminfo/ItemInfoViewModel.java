package ru.fitsme.android.presentation.fragments.iteminfo;

import androidx.annotation.NonNull;

import ru.fitsme.android.domain.interactors.clothes.IClothesInteractor;
import ru.fitsme.android.presentation.fragments.base.BaseViewModel;

public class ItemInfoViewModel extends BaseViewModel {

    private final IClothesInteractor clothesInteractor;

    public ItemInfoViewModel(@NonNull IClothesInteractor clothesInteractor) {
        this.clothesInteractor = clothesInteractor;
    }

    void init() {
    }
}
