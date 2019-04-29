package ru.fitsme.android.presentation.fragments.rateitems.view;

public class RateItemsState {
    private int index;
    private IOnSwipeListener.AnimationType animationType;

    RateItemsState(int index, IOnSwipeListener.AnimationType animationType) {
        this.index = index;
        this.animationType = animationType;
    }

    public int getIndex() {
        return index;
    }

    IOnSwipeListener.AnimationType getAnimationType() {
        return animationType;
    }
}
