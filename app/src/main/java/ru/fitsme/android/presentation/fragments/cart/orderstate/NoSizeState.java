package ru.fitsme.android.presentation.fragments.cart.orderstate;

import android.view.View;

import ru.fitsme.android.presentation.fragments.cart.CartAdapter;

public class NoSizeState extends OrderState {

    public NoSizeState(CartAdapter.NormalViewHolder viewHolder, CartAdapter.OnItemClickCallback callback) {
        super(viewHolder, callback);
        this.viewHolder.imageView.setAlpha(0.5f);
        this.viewHolder.brandName.setAlpha(0.5f);
        this.viewHolder.name.setAlpha(0.5f);
        this.viewHolder.price.setVisibility(View.INVISIBLE);
        this.viewHolder.noSize.setVisibility(View.VISIBLE);
    }
}
