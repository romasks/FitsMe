package ru.fitsme.android.presentation.fragments.cart.orderstate;

import ru.fitsme.android.presentation.fragments.cart.CartAdapter;

public class NoSizeState extends OrderState {

    public NoSizeState(CartAdapter.NormalViewHolder viewHolder, CartAdapter.OnItemClickCallback callback) {
        super(viewHolder, callback);
        this.viewHolder.imageView.setAlpha(0.5f);
        this.viewHolder.brandName.setAlpha(0.5f);
        this.viewHolder.name.setAlpha(0.5f);
        this.viewHolder.priceHint.setAlpha(0.5f);
        this.viewHolder.price.setAlpha(0.5f);
        this.viewHolder.sizeHint.setAlpha(0.5f);
        this.viewHolder.size.setAlpha(0.5f);
    }
}
