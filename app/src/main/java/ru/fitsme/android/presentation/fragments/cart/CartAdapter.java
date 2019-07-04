package ru.fitsme.android.presentation.fragments.cart;

import android.arch.paging.PagedListAdapter;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import ru.fitsme.android.BR;
import ru.fitsme.android.R;
import ru.fitsme.android.data.models.ClothesItemModel;
import ru.fitsme.android.databinding.ItemCartBinding;
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

        GenericViewHolder(ViewDataBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(int position) {
            OrderItem orderItem = getItem(position);
            ClothesItemModel clothesItem = new ClothesItemModel(orderItem.getClothe());

            binding.setVariable(BR.clothesItem, clothesItem);
            binding.executePendingBindings();
        }
    }
}
