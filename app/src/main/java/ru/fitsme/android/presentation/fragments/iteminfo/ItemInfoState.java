package ru.fitsme.android.presentation.fragments.iteminfo;

import android.support.annotation.NonNull;

import ru.fitsme.android.domain.entities.clothes.ClothesItem;

class ItemInfoState {
    private ClothesItem clothesItem;
    private State state;

    ItemInfoState(@NonNull ClothesItem clothesItem) {
        this.clothesItem = clothesItem;
        this.state = State.OK;
    }

    ItemInfoState(@NonNull State state) {
        this.state = state;
    }

    ClothesItem getClothesItem() {
        return clothesItem;
    }

    State getState() {
        return state;
    }

    enum State {
        LOADING, ERROR, OK
    }
}
