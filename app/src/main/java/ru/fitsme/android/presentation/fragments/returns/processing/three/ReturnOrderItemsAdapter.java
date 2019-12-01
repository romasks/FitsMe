package ru.fitsme.android.presentation.fragments.returns.processing.three;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.CheckBox;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import ru.fitsme.android.BR;
import ru.fitsme.android.R;
import ru.fitsme.android.databinding.ItemReturnItemBinding;
import ru.fitsme.android.domain.entities.order.OrderItem;

public class ReturnOrderItemsAdapter extends RecyclerView.Adapter<ReturnOrderItemsAdapter.ReturnOrderItemsViewHolder> {

    private ChooseItemReturnViewModel viewModel;
    private List<OrderItem> items;
    private int selectedItemsCount = 0;

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

    void setItems(List<OrderItem> items) {
        this.items = items;
    }

    private OrderItem getItem(int position) {
        return items.get(position);
    }

    boolean noItemsSelected() {
        return selectedItemsCount == 0;
    }

    class ReturnOrderItemsViewHolder extends RecyclerView.ViewHolder {
        final public ViewDataBinding binding;

        ReturnOrderItemsViewHolder(ViewDataBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(int position) {
            ((CheckBox) binding.getRoot().findViewById(R.id.cb)).setOnCheckedChangeListener((compoundButton, b) -> {
                getItem(position).getClothe().setCheckedForReturn(b);
                if (b) {
                    selectedItemsCount++;
                } else {
                    selectedItemsCount--;
                }
            });

            binding.setVariable(BR.orderItem, getItem(position));
            binding.setVariable(BR.viewModel, viewModel);
            binding.setVariable(BR.position, position);
            binding.executePendingBindings();
        }
    }
}
