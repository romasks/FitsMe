package ru.fitsme.android.presentation.fragments.orders.details;

import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import ru.fitsme.android.R;
import ru.fitsme.android.databinding.FragmentOrderDetailsBinding;
import ru.fitsme.android.domain.entities.order.Order;
import ru.fitsme.android.presentation.common.listener.BackClickListener;
import ru.fitsme.android.presentation.fragments.base.BaseFragment;

public class OrderDetailsFragment extends BaseFragment<OrderDetailsViewModel> implements OrderDetailsBindingEvents, BackClickListener {

    private static final String KEY_ORDER = "ORDER";

    private FragmentOrderDetailsBinding binding;
    private OrderDetailsAdapter adapter;
    private Order order;

    public static OrderDetailsFragment newInstance(Order order) {
        Bundle args = new Bundle();
        args.putParcelable(KEY_ORDER, order);
        OrderDetailsFragment fragment = new OrderDetailsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_order_details;
    }

    @Override
    protected void afterCreateView(View view) {
        if (getArguments() != null) {
            order = getArguments().getParcelable(KEY_ORDER);
        }

        binding = FragmentOrderDetailsBinding.bind(view);
        binding.setBindingEvents(this);
        binding.setViewModel(viewModel);
        binding.setOrder(order);

        binding.appBar.setBackClickListener(this);
        binding.appBar.setTitle(getResources().getString(R.string.orders_details_header));
    }

    @Override
    protected void setUpRecyclers() {
        adapter = new OrderDetailsAdapter(viewModel);
        adapter.setItems(order.getOrderItemList());

        binding.orderItemsListRv.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.orderItemsListRv.setHasFixedSize(true);
        binding.orderItemsListRv.setAdapter(adapter);
    }

    @Override
    public void goBack() {
        viewModel.onBackPressed();
    }
}
