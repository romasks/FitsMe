package ru.fitsme.android.presentation.fragments.returns.processing.two;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import javax.inject.Inject;

import ru.fitsme.android.R;
import ru.fitsme.android.databinding.FragmentReturnChooseOrderBinding;
import ru.fitsme.android.domain.entities.order.Order;
import ru.fitsme.android.domain.interactors.orders.IOrdersInteractor;
import ru.fitsme.android.presentation.common.listener.BackClickListener;
import ru.fitsme.android.presentation.fragments.base.BaseFragment;
import ru.fitsme.android.presentation.fragments.base.ViewModelFactory;
import ru.fitsme.android.presentation.fragments.main.MainFragment;

public class ChooseOrderReturnFragment extends BaseFragment<ChooseOrderReturnViewModel> implements ChooseOrderReturnBindingEvents, BackClickListener {

    private FragmentReturnChooseOrderBinding binding;
    private ReturnOrdersAdapter adapter;

    public static ChooseOrderReturnFragment newInstance() {
        return new ChooseOrderReturnFragment();
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_return_choose_order, container, false);
        binding.setBindingEvents(this);
        binding.appBar.setBackClickListener(this);
        binding.appBar.setTitle(getResources().getString(R.string.returns_choose_order_header));
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = ViewModelProviders.of(this,
                new ViewModelFactory()).get(ChooseOrderReturnViewModel.class);
        if (savedInstanceState == null) {
            viewModel.init();
        }
        binding.setViewModel(viewModel);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        adapter = new ReturnOrdersAdapter(viewModel);

        binding.returnOrdersListRv.setLayoutManager(linearLayoutManager);
        binding.returnOrdersListRv.setHasFixedSize(true);
        binding.returnOrdersListRv.setAdapter(adapter);

        viewModel.getReturnsOrdersLiveData().observe(getViewLifecycleOwner(), this::onLoadPage);

        viewModel.getReturnsOrdersIsEmpty().observe(getViewLifecycleOwner(), this::onReturnsOrdersIsEmpty);
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
        viewModel.backToReturnsHowTo();
    }

    @Override
    public void onClickGoToCart() {
        if (getParentFragment() != null) {
            ((MainFragment) getParentFragment()).goToCart();
        }
    }

    @Override
    public void onBackPressed() {
        viewModel.onBackPressed();
    }
}
