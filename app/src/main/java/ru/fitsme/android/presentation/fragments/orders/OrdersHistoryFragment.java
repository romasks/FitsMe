package ru.fitsme.android.presentation.fragments.orders;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import ru.fitsme.android.R;
import ru.fitsme.android.databinding.FragmentOrdersHistoryBinding;
import ru.fitsme.android.domain.entities.order.Order;
import ru.fitsme.android.presentation.common.listener.BackClickListener;
import ru.fitsme.android.presentation.fragments.base.BaseFragment;
import ru.fitsme.android.presentation.fragments.base.ViewModelFactory;
import ru.fitsme.android.presentation.fragments.main.MainFragment;

public class OrdersHistoryFragment extends BaseFragment<OrdersHistoryViewModel> implements OrdersHistoryBindingEvents, BackClickListener {

    private FragmentOrdersHistoryBinding binding;
    private OrdersHistoryAdapter adapter;

    public static OrdersHistoryFragment newInstance() {
        return new OrdersHistoryFragment();
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_orders_history, container, false);
        binding.setBindingEvents(this);
        binding.appBar.setBackClickListener(this);
        binding.appBar.setTitle(getResources().getString(R.string.returns_choose_order_header));
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = ViewModelProviders.of(this,
                new ViewModelFactory()).get(OrdersHistoryViewModel.class);
        if (savedInstanceState == null) {
            viewModel.init();
        }
        binding.setViewModel(viewModel);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        adapter = new OrdersHistoryAdapter(viewModel);

        binding.returnOrdersListRv.setLayoutManager(linearLayoutManager);
        binding.returnOrdersListRv.setHasFixedSize(true);
        binding.returnOrdersListRv.setAdapter(adapter);

        viewModel.getOrdersListLiveData().observe(this, this::onLoadPage);

        viewModel.getOrdersListIsEmpty().observe(this, this::onReturnsOrdersIsEmpty);
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
