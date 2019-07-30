package ru.fitsme.android.presentation.fragments.cart;

import android.arch.paging.PagedListAdapter;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import ru.fitsme.android.BR;
import ru.fitsme.android.R;
import ru.fitsme.android.data.models.ClothesItemModel;
import ru.fitsme.android.databinding.ItemCartBinding;
import ru.fitsme.android.domain.entities.clothes.ClothesItem;
import ru.fitsme.android.domain.entities.order.OrderItem;

public class CartAdapter extends PagedListAdapter<OrderItem, CartAdapter.GenericViewHolder> {

    CartAdapter() {
        super(CartFragment.DIFF_CALLBACK);
    }

    @NonNull
    @Override
    public CartAdapter.GenericViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ItemCartBinding binding = DataBindingUtil.inflate(layoutInflater, R.layout.item_cart, parent, false);
        return new CartAdapter.GenericViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CartAdapter.GenericViewHolder holder, int position) {
        holder.bind(position);
    }

    class GenericViewHolder extends RecyclerView.ViewHolder {
        final ViewDataBinding binding;
        final ImageView rightDeleteIcon;
        final ImageView leftDeleteIcon;
        RelativeLayout viewBackground;
        RelativeLayout viewForeground;

        GenericViewHolder(ViewDataBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            viewBackground = binding.getRoot().findViewById(R.id.item_cart_view_background);
            viewForeground = binding.getRoot().findViewById(R.id.item_cart_view_foreground);
            rightDeleteIcon = binding.getRoot().findViewById(R.id.item_cart_delete_icon_right);
            leftDeleteIcon = binding.getRoot().findViewById(R.id.item_cart_delete_icon_left);
        }

        void bind(int position) {
            OrderItem orderItem = getItem(position);
            ClothesItem clothesItem = orderItem.getClothe();

            binding.setVariable(BR.clotheItem, clothesItem);
            binding.executePendingBindings();
        }
    }
}
