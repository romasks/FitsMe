package ru.fitsme.android.presentation.fragments.cart;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hendraanggrian.widget.PaginatedRecyclerView;

import java.util.List;

import ru.fitsme.android.BR;
import ru.fitsme.android.data.models.ClothesItemModel;
import ru.fitsme.android.domain.entities.order.OrderItem;
import ru.fitsme.android.presentation.fragments.base.BaseViewModel;

public class CartAdapter extends PaginatedRecyclerView.Adapter<CartAdapter.ViewHolder> {

    private int layoutId;
    private List<OrderItem> items;
    private BaseViewModel viewModel;

    public CartAdapter(@LayoutRes int layoutId, BaseViewModel viewModel) {
        this.layoutId = layoutId;
        this.viewModel = viewModel;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ViewDataBinding binding = DataBindingUtil.inflate(layoutInflater, viewType, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        viewHolder.bind(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items == null ? 0 : items.size();
    }

    @Override
    public int getItemViewType(int position) {
        return getLayoutIdForPosition(position);
    }

    private int getLayoutIdForPosition(int position) {
        return layoutId;
    }

    public void setOrderItems(List<OrderItem> items) {
        this.items = items;
    }

    public void addOrderItems(List<OrderItem> items) {
        this.items.addAll(items);
    }

    public OrderItem getOrderItemAt(int position) {
        return items.get(position);
    }

    public void removeItemAt(int position) {
        items.remove(getOrderItemAt(position));
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        final ViewDataBinding binding;

        ViewHolder(@NonNull ViewDataBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }

        public void bind(OrderItem item) {
            ClothesItemModel clothesItem = new ClothesItemModel(item.getClothe());
            binding.setVariable(BR.clothesItem, clothesItem);
            binding.setVariable(BR.price, item.getPrice());
            binding.executePendingBindings();
        }
    }
}
