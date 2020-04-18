package ru.fitsme.android.presentation.fragments.orders;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import ru.fitsme.android.BR;
import ru.fitsme.android.R;
import ru.fitsme.android.databinding.ItemOrdersHistoryBinding;
import ru.fitsme.android.domain.entities.order.Order;

public class OrdersHistoryAdapter extends PagedListAdapter<Order, OrdersHistoryAdapter.ReturnOrdersViewHolder> {

    private OrdersHistoryViewModel viewModel;

    OrdersHistoryAdapter(OrdersHistoryViewModel viewModel) {
        super(Order.DIFF_CALLBACK);
        this.viewModel = viewModel;
    }

    @NonNull
    @Override
    public ReturnOrdersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ItemOrdersHistoryBinding binding = DataBindingUtil.inflate(layoutInflater, R.layout.item_orders_history, parent, false);
        return new ReturnOrdersViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ReturnOrdersViewHolder holder, int position) {
        holder.bind(position);
    }

    public class ReturnOrdersViewHolder extends RecyclerView.ViewHolder {
        final public ViewDataBinding binding;

        ReturnOrdersViewHolder(ViewDataBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(int position) {
            Order order = getItem(position);

            binding.setVariable(BR.order, order);
            binding.setVariable(BR.viewModel, viewModel);
            binding.setVariable(BR.position, position);
            binding.executePendingBindings();
        }
    }
}
