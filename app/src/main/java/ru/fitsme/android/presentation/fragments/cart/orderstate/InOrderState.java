package ru.fitsme.android.presentation.fragments.cart.orderstate;

import ru.fitsme.android.presentation.fragments.cart.CartAdapter;

public class InOrderState extends OrderState {

    public InOrderState(CartAdapter.NormalViewHolder viewHolder, CartAdapter.OnItemClickCallback callback) {
        super(viewHolder, callback);
        this.viewHolder.imageView.setAlpha(1f);
        this.viewHolder.brandName.setAlpha(1f);
        this.viewHolder.name.setAlpha(1f);
        this.viewHolder.priceHint.setAlpha(1f);
        this.viewHolder.price.setAlpha(1f);
        this.viewHolder.sizeHint.setAlpha(1f);
        this.viewHolder.size.setAlpha(1f);
//        this.viewHolder.noSize.setVisibility(View.INVISIBLE);
    }
}
