package ru.fitsme.android.presentation.fragments.iteminfo.states;

import android.view.View;

import ru.fitsme.android.databinding.FragmentItemInfoBinding;
import ru.fitsme.android.presentation.fragments.iteminfo.ClotheInfo;

public class RateItemState extends ItemInfoState {

    public RateItemState(ClotheInfo clotheInfo, StateSettable stateSettable, FragmentItemInfoBinding binding) {
        super(clotheInfo, stateSettable, binding);
        binding.itemInfoAddToCartBtn.setVisibility(View.GONE);
        binding.itemInfoRemoveBtn.setVisibility(View.GONE);
    }

    @Override
    public void onClickAdd() {

    }

    @Override
    public void onClickRemove() {

    }
}
