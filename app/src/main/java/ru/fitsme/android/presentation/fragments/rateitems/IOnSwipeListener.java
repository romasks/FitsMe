package ru.fitsme.android.presentation.fragments.rateitems;

public interface IOnSwipeListener {
    void onSwipe(RateItemsFragment.AnimationType animationType);

    enum AnimationType {
        LEFT, RIGHT, NONE, SIMPLE
    }
}
