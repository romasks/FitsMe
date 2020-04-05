package ru.fitsme.android.presentation.fragments.iteminfo.states;

import android.view.View;

import ru.fitsme.android.R;
import ru.fitsme.android.app.App;
import ru.fitsme.android.databinding.FragmentItemInfoBinding;
import ru.fitsme.android.domain.entities.clothes.ClothesItem;
import ru.fitsme.android.presentation.fragments.iteminfo.ClotheInfo;

public class InCartState extends ItemInfoState {

    public InCartState(ClotheInfo clotheInfo, StateSettable stateSettable, FragmentItemInfoBinding binding) {
        super(clotheInfo, stateSettable, binding);
        binding.itemInfoAddToCartBtn.setBackgroundResource(R.drawable.bg_in_cart_btn);
        binding.itemInfoAddToCartBtn.setAlpha(0.5f);
        binding.itemInfoAddToCartBtn.setEnabled(false);
        binding.itemInfoAddToCartBtn.setText(R.string.item_info_in_cart);
        binding.itemInfoAddToCartBtn.setTextColor(App.getInstance().getResources().getColor(R.color.black));
        binding.itemInfoAddToCartBtn.setVisibility(View.VISIBLE);
        binding.itemInfoRemoveBtn.setVisibility(View.VISIBLE);
        binding.itemInfoRemoveBtn.setText(R.string.item_info_remove_from_favourite);
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
