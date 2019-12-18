package ru.fitsme.android.presentation.fragments.orders;

import android.os.Bundle;
import android.view.View;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import ru.fitsme.android.R;
import ru.fitsme.android.databinding.FragmentOrdersHistoryBinding;
import ru.fitsme.android.domain.entities.order.Order;
import ru.fitsme.android.presentation.common.listener.BackClickListener;
import ru.fitsme.android.presentation.fragments.base.BaseFragment;
import ru.fitsme.android.presentation.fragments.main.MainFragment;

public class OrdersHistoryFragment extends BaseFragment<OrdersHistoryViewModel> implements OrdersHistoryBindingEvents, BackClickListener {

    private FragmentOrdersHistoryBinding binding;
    private OrdersHistoryAdapter adapter;

    public static OrdersHistoryFragment newInstance() {
        return new OrdersHistoryFragment();
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_orders_history;
    }

    @Override
    protected void afterCreateView(View view) {
        binding = FragmentOrdersHistoryBinding.bind(view);
        binding.setBindingEvents(this);
        binding.setViewModel(viewModel);

        binding.appBar.setBackClickListener(this);
        binding.appBar.setTitle(getResources().getString(R.string.returns_choose_order_header));
    }

    @Override
    protected void setUpRecyclers() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        adapter = new OrdersHistoryAdapter(viewModel);

        binding.returnOrdersListRv.setLayoutManager(linearLayoutManager);
        binding.returnOrdersListRv.setHasFixedSize(true);
        binding.returnOrdersListRv.setAdapter(adapter);
    }

    @Override
    protected void setUpObservers() {
        viewModel.getOrdersListLiveData().observe(this, this::onLoadPage);
        viewModel.getOrdersListIsEmpty().observe(this, this::onReturnsOrdersIsEmpty);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    private void onReturnsOrdersIsEmpty(Boolean isEmpty) {
        if (isEmpty) {
            binding.returnsOrderNoItems.setVisibility(View.VISIBLE);
        } else {
            binding.returnsOrderNoItems.setVisibility(View.GONE);
        }
    }

    private void onLoadPage(List<Order> ordersList) {
        adapter.setItems(ordersList);
    }

    @Override
    public void goBack() {
        viewModel.onBackPressed();
    }

    @Override
    public void onClickGoToCatalog() {
        if (getParentFragment() != null) {
            ((MainFragment) getParentFragment()).goToFavourites();
        }
    }

    @Override
    public void onBackPressed() {
        viewModel.onBackPressed();
    }
}
