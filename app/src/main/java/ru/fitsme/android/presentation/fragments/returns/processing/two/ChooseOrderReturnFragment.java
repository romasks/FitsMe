package ru.fitsme.android.presentation.fragments.returns.processing.two;

import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.List;

import ru.fitsme.android.R;
import ru.fitsme.android.databinding.FragmentReturnChooseOrderBinding;
import ru.fitsme.android.domain.entities.order.Order;
import ru.fitsme.android.presentation.common.listener.BackClickListener;
import ru.fitsme.android.presentation.fragments.base.BaseFragment;
import ru.fitsme.android.presentation.fragments.main.MainFragment;

public class ChooseOrderReturnFragment extends BaseFragment<ChooseOrderReturnViewModel> implements ChooseOrderReturnBindingEvents, BackClickListener {

    private FragmentReturnChooseOrderBinding binding;
    private ReturnOrdersAdapter adapter;

    public static ChooseOrderReturnFragment newInstance() {
        return new ChooseOrderReturnFragment();
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_return_choose_order;
    }

    @Override
    protected void afterCreateView(View view) {
        binding = FragmentReturnChooseOrderBinding.bind(view);
        binding.setBindingEvents(this);
        binding.setViewModel(viewModel);
        binding.appBar.setBackClickListener(this);
        binding.appBar.setTitle(getResources().getString(R.string.returns_choose_order_header));
    }

    @Override
    protected void setUpRecyclers() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        adapter = new ReturnOrdersAdapter(viewModel);

        binding.returnOrdersListRv.setLayoutManager(linearLayoutManager);
        binding.returnOrdersListRv.setHasFixedSize(true);
        binding.returnOrdersListRv.setAdapter(adapter);
    }

    @Override
    protected void setUpObservers() {
        viewModel.getReturnsOrdersLiveData().observe(getViewLifecycleOwner(), this::onLoadPage);
        viewModel.getReturnsOrdersIsEmpty().observe(getViewLifecycleOwner(), this::onReturnsOrdersIsEmpty);
    }

    private void onReturnsOrdersIsEmpty(Boolean isEmpty) {
        binding.returnsOrderNoItems.setVisibility(isEmpty ? View.VISIBLE : View.GONE);
    }

    private void onLoadPage(List<Order> ordersList) {
        adapter.setItems(ordersList);
    }

    @Override
    public void goBack() {
        viewModel.onBackPressed();
    }

    @Override
    public void onClickGoToCart() {
        if (getParentFragment() != null) {
            ((MainFragment) getParentFragment()).goToCart();
        }
    }
}
