package ru.fitsme.android.presentation.fragments.returns.details;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import ru.fitsme.android.BR;
import ru.fitsme.android.R;
import ru.fitsme.android.databinding.ItemReturnDetailsItemBinding;
import ru.fitsme.android.domain.entities.returns.ReturnsOrderItem;

public class ReturnDetailsAdapter extends RecyclerView.Adapter<ReturnDetailsAdapter.ReturnDetailsItemViewHolder> {

    private ReturnDetailsViewModel viewModel;
    private List<ReturnsOrderItem> returnsOrderItems = new ArrayList<>();

    ReturnDetailsAdapter(ReturnDetailsViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @NonNull
    @Override
    public ReturnDetailsItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ItemReturnDetailsItemBinding binding = DataBindingUtil.inflate(layoutInflater, R.layout.item_return_details_item, parent, false);
        return new ReturnDetailsItemViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ReturnDetailsItemViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return returnsOrderItems.size();
    }

    void setItems(List<ReturnsOrderItem> items) {
        returnsOrderItems.clear();
        returnsOrderItems.addAll(items);
        notifyDataSetChanged();
    }

    private ReturnsOrderItem getItem(int position) {
        return returnsOrderItems.get(position);
    }

    class ReturnDetailsItemViewHolder extends RecyclerView.ViewHolder {
        final public ViewDataBinding binding;

        ReturnDetailsItemViewHolder(ViewDataBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(int position) {
            binding.setVariable(BR.orderItem, getItem(position).getOrderItem());
            binding.setVariable(BR.viewModel, viewModel);
            binding.setVariable(BR.position, position);
            binding.executePendingBindings();
        }
    }
}
