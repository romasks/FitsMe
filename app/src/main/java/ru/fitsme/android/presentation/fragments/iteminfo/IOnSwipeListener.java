package ru.fitsme.android.presentation.fragments.iteminfo;

import ru.fitsme.android.presentation.fragments.rateitems.view.RateItemsFragment;

public interface IOnSwipeListener {
    void onSwipe(RateItemsFragment.AnimationType animationType);

    public enum AnimationType {
        LEFT, RIGHT, NONE, SIMPLE
    }
}
