package ru.fitsme.android.presentation.fragments.iteminfo;

import android.support.annotation.NonNull;

import ru.fitsme.android.data.models.ClothesItemModel;
import ru.fitsme.android.domain.entities.clothes.ClothesItem;

class ItemInfoState {
    private ClothesItemModel clothesItem;
    private State state;

    ItemInfoState(@NonNull ClothesItem clothesItem) {
        this.clothesItem = new ClothesItemModel(clothesItem);
        this.state = State.OK;
    }

    ItemInfoState(@NonNull State state) {
        this.state = state;
    }

    ClothesItemModel getClothesItem() {
        return clothesItem;
    }

    State getState() {
        return state;
    }

    enum State {
        LOADING, ERROR, OK
    }
}
