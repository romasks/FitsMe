package ru.fitsme.android.presentation.fragments.rateitems.view;

public interface IOnSwipeListener {
    void onSwipe(RateItemsFragment.AnimationType animationType);

    enum AnimationType {
        LEFT, RIGHT, NONE, SIMPLE
    }
}
