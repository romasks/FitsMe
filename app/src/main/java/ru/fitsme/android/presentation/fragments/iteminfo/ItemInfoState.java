package ru.fitsme.android.presentation.fragments.iteminfo;

import android.support.annotation.NonNull;

import ru.fitsme.android.domain.entities.clothes.ClothesItem;

public class ItemInfoState {
    private ClothesItem clothesItem;
    private State state;

    public ItemInfoState(@NonNull ClothesItem clothesItem) {
        this.clothesItem = clothesItem;
        this.state = State.OK;
    }

    public ItemInfoState(@NonNull State state) {
        this.state = state;
    }

    public ClothesItem getClothesItem() {
        return clothesItem;
    }

    public State getState() {
        return state;
    }

    enum State {
        LOADING, ERROR, OK
    }
}
