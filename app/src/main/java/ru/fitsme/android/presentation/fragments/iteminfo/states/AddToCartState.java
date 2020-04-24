package ru.fitsme.android.presentation.fragments.iteminfo.states;

import android.view.View;

import ru.fitsme.android.R;
import ru.fitsme.android.app.App;
import ru.fitsme.android.databinding.FragmentItemInfoBinding;
import ru.fitsme.android.domain.entities.clothes.ClothesItem;
import ru.fitsme.android.presentation.fragments.iteminfo.ClotheInfo;

public class AddToCartState extends ItemInfoState {

    public AddToCartState(ClotheInfo clotheInfo, StateSettable stateSettable, FragmentItemInfoBinding binding) {
        super(clotheInfo, stateSettable, binding);
        binding.itemInfoAddToCartBtn.setBackgroundResource(R.drawable.bg_to_cart_btn);
        binding.itemInfoAddToCartBtn.setAlpha(1);
        binding.itemInfoAddToCartBtn.setText(R.string.item_info_add_to_cart);
        binding.itemInfoAddToCartBtn.setTextColor(App.getInstance().getResources().getColor(R.color.white));
        binding.itemInfoAddToCartBtn.setVisibility(View.VISIBLE);
        binding.itemInfoAddToCartBtn.setEnabled(true);
        binding.itemInfoRemoveBtn.setVisibility(View.VISIBLE);
        binding.itemInfoRemoveBtn.setText(R.string.item_info_remove_from_favourite);
    }

    @Override
    public void onClickAdd() {
        ClothesItem item = getClotheItem();
        clotheInfo.getCallback().add(item)
                .subscribe(orderItem -> {
                    if (orderItem.getClothe() != null) {
                        stateSettable.setState(new InCartState(clotheInfo, stateSettable, binding));
                    }
                });
    }

    @Override
    public void onClickRemove() {
        ClothesItem item = getClotheItem();
        clotheInfo.getCallback().remove(item);
        stateSettable.finish();
    }
}
