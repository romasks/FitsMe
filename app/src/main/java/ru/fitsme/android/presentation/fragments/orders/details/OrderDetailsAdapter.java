package ru.fitsme.android.presentation.fragments.orders.details;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import ru.fitsme.android.BR;
import ru.fitsme.android.R;
import ru.fitsme.android.databinding.ItemOrderDetailsBinding;
import ru.fitsme.android.domain.entities.order.OrderItem;

public class OrderDetailsAdapter extends RecyclerView.Adapter<OrderDetailsAdapter.OrderDetailsItemViewHolder> {

    private OrderDetailsViewModel viewModel;
    private List<OrderItem> orderItems = new ArrayList<>();

    OrderDetailsAdapter(OrderDetailsViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @NonNull
    @Override
    public OrderDetailsItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ItemOrderDetailsBinding binding = DataBindingUtil.inflate(layoutInflater, R.layout.item_order_details, parent, false);
        return new OrderDetailsItemViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderDetailsItemViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return orderItems == null ? 0 : orderItems.size();
    }

    void setItems(List<OrderItem> items) {
        orderItems.clear();
        orderItems.addAll(items);
        notifyDataSetChanged();
    }

    private OrderItem getItem(int position) {
        return orderItems.get(position);
    }

    class OrderDetailsItemViewHolder extends RecyclerView.ViewHolder {
        final public ViewDataBinding binding;

        OrderDetailsItemViewHolder(ViewDataBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(int position) {
            binding.setVariable(BR.orderItem, getItem(position));
            binding.setVariable(BR.viewModel, viewModel);
            binding.setVariable(BR.position, position);
            binding.executePendingBindings();
        }
    }
}
