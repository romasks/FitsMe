package ru.fitsme.android.presentation.fragments.rateitems;

import ru.fitsme.android.domain.entities.clothes.ClothesItem;
import ru.fitsme.android.presentation.fragments.iteminfo.ClotheInfo;

public class RateItemsState {
    private ClotheInfo clotheInfo;
    private IOnSwipeListener.AnimationType animationType;

    RateItemsState(ClotheInfo clotheInfo, IOnSwipeListener.AnimationType animationType) {
        this.clotheInfo = clotheInfo;
        this.animationType = animationType;
    }

    IOnSwipeListener.AnimationType getAnimationType() {
        return animationType;
    }

    public ClotheInfo getClotheInfo() {
        return clotheInfo;
    }
}
