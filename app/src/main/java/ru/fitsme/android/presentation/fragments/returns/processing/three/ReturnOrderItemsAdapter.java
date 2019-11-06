package ru.fitsme.android.presentation.fragments.returns.processing.three;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ru.fitsme.android.BR;
import ru.fitsme.android.R;
import ru.fitsme.android.databinding.ItemReturnItemBinding;
import ru.fitsme.android.domain.entities.clothes.ClothesItem;

public class ReturnOrderItemsAdapter extends RecyclerView.Adapter<ReturnOrderItemsAdapter.ReturnOrderItemsViewHolder> {

    private ChooseItemReturnViewModel viewModel;
    private List<ClothesItem> items;

    ReturnOrderItemsAdapter(ChooseItemReturnViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @NonNull
    @Override
    public ReturnOrderItemsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ItemReturnItemBinding binding = DataBindingUtil.inflate(layoutInflater, R.layout.item_return_item, parent, false);
        return new ReturnOrderItemsViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ReturnOrderItemsViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return items == null ? 0 : items.size();
    }

    void setItems(List<ClothesItem> items) {
        this.items = items;
    }

    private ClothesItem getItem(int position) {
        return items.get(position);
    }

    class ReturnOrderItemsViewHolder extends RecyclerView.ViewHolder {
        final public ViewDataBinding binding;

        ReturnOrderItemsViewHolder(ViewDataBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(int position) {
            binding.setVariable(BR.clothesItem, getItem(position));
            binding.setVariable(BR.viewModel, viewModel);
            binding.setVariable(BR.position, position);
            binding.executePendingBindings();
        }
    }
}
