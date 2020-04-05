package ru.fitsme.android.presentation.fragments.iteminfo.states;

import android.view.View;

import ru.fitsme.android.R;
import ru.fitsme.android.databinding.FragmentItemInfoBinding;
import ru.fitsme.android.domain.entities.clothes.ClothesItem;
import ru.fitsme.android.presentation.fragments.iteminfo.ClotheInfo;

public class RemoveFromCartState extends ItemInfoState {

    public RemoveFromCartState(ClotheInfo clotheInfo, StateSettable stateSettable, FragmentItemInfoBinding binding) {
        super(clotheInfo, stateSettable, binding);
        binding.itemInfoAddToCartBtn.setVisibility(View.GONE);
        binding.itemInfoAddToCartBtn.setEnabled(false);
        binding.itemInfoRemoveBtn.setVisibility(View.VISIBLE);
        binding.itemInfoRemoveBtn.setText(R.string.item_info_remove_from_cart);
    }

    @Override
    public void onClickAdd() {

    }

    @Override
    public void onClickRemove() {
        ClothesItem item = getClotheItem();
        clotheInfo.getCallback().remove(item);
        stateSettable.finish();
    }
}
