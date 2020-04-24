package ru.fitsme.android.presentation.fragments.iteminfo.states;

import ru.fitsme.android.databinding.FragmentItemInfoBinding;
import ru.fitsme.android.domain.entities.clothes.ClothesItem;
import ru.fitsme.android.domain.entities.clothes.LikedClothesItem;
import ru.fitsme.android.presentation.fragments.iteminfo.ClotheInfo;

public abstract class ItemInfoState {
    ClotheInfo clotheInfo;
    StateSettable stateSettable;
    FragmentItemInfoBinding binding;

    public ItemInfoState(ClotheInfo clotheInfo, StateSettable stateSettable, FragmentItemInfoBinding binding) {
        this.clotheInfo = clotheInfo;
        this.stateSettable = stateSettable;
        this.binding = binding;
    }

    ClothesItem getClotheItem(){
        ClothesItem item;
        if (clotheInfo.getClothe() instanceof ClothesItem) {
            item = (ClothesItem) clotheInfo.getClothe();
        } else if (clotheInfo.getClothe() instanceof LikedClothesItem) {
            LikedClothesItem likedItem = (LikedClothesItem) clotheInfo.getClothe();
            item = likedItem.getClothe();
        } else {
            throw new IllegalArgumentException("Inappropriate ClotheInfo");
        }
        return item;
    }

    public abstract void onClickAdd();
    public abstract void onClickRemove();

    public interface StateSettable {
        void setState(ItemInfoState state);
        void finish();
    }
}
