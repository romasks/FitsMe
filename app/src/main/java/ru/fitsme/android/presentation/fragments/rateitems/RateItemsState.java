package ru.fitsme.android.presentation.fragments.rateitems;

import ru.fitsme.android.presentation.fragments.iteminfo.ClotheInfo;

class RateItemsState {
    private ClotheInfo clotheInfo;
    private IOnSwipeListener.AnimationType animationType;

    RateItemsState(ClotheInfo clotheInfo, IOnSwipeListener.AnimationType animationType) {
        this.clotheInfo = clotheInfo;
        this.animationType = animationType;
    }

    IOnSwipeListener.AnimationType getAnimationType() {
        return animationType;
    }

    ClotheInfo getClotheInfo() {
        return clotheInfo;
    }
}
