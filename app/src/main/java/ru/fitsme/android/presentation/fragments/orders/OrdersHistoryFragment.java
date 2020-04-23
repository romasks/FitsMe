package ru.fitsme.android.presentation.fragments.orders;

import android.view.View;

import androidx.paging.PagedList;
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
        setUp();
    }

    private void setUp() {
        if (getParentFragment() != null) {
            ((MainFragment) getParentFragment()).hideBottomNavbar();
        }
    }

    @Override
    protected void setUpRecyclers() {
        adapter = new OrdersHistoryAdapter(viewModel);

        binding.returnOrdersListRv.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.returnOrdersListRv.setHasFixedSize(true);
        binding.returnOrdersListRv.setAdapter(adapter);
    }

    @Override
    protected void setUpObservers() {
        viewModel.getOrdersListLiveData().observe(getViewLifecycleOwner(), this::onLoadPage);
        viewModel.getOrdersListIsEmpty().observe(getViewLifecycleOwner(), this::onOrdersListIsEmpty);
    }

    private void onLoadPage(PagedList<Order> ordersList) {
        adapter.submitList(ordersList);
        binding.loadingTv.setVisibility(View.GONE);
    }

    private void onOrdersListIsEmpty(Boolean hasNoItems) {
        binding.ordersHistoryNoItems.setVisibility(hasNoItems ? View.VISIBLE : View.GONE);
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
}
