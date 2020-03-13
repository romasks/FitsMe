package ru.fitsme.android.presentation.fragments.cart.orderstate;

import ru.fitsme.android.presentation.fragments.cart.CartAdapter;
import ru.fitsme.android.presentation.fragments.cart.CartViewModel;

public abstract class OrderState {
    CartAdapter.NormalViewHolder viewHolder;
    CartAdapter.OnItemClickCallback callback;

    OrderState(CartAdapter.NormalViewHolder viewHolder, CartAdapter.OnItemClickCallback callback){
        this.viewHolder = viewHolder;
        this.callback = callback;
        this.viewHolder.binding.getRoot().setOnClickListener(v -> {
            callback.setDetailView(viewHolder.getOrderItem());
        });
    }
}
