package ru.fitsme.android.presentation.fragments.returns.processing.six;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

import ru.fitsme.android.R;
import ru.fitsme.android.databinding.FragmentReturnVerifyDataBinding;
import ru.fitsme.android.domain.entities.returns.ReturnsOrder;
import ru.fitsme.android.presentation.common.listener.BackClickListener;
import ru.fitsme.android.presentation.fragments.base.BaseFragment;
import ru.fitsme.android.presentation.fragments.main.MainFragment;

public class VerifyDataReturnFragment extends BaseFragment<VerifyDataReturnViewModel> implements VerifyDataReturnBindingEvents, BackClickListener {

    private static final String KEY_RETURN_ID = "RETURN_ID";

    private FragmentReturnVerifyDataBinding binding;
    private SelectedReturnOrderItemsAdapter adapter;
    private int returnId;

    public static VerifyDataReturnFragment newInstance(int returnId) {
        Bundle args = new Bundle();
        args.putInt(KEY_RETURN_ID, returnId);
        VerifyDataReturnFragment fragment = new VerifyDataReturnFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_return_verify_data;
    }

    @Override
    protected void afterCreateView(View view) {
        binding = FragmentReturnVerifyDataBinding.bind(view);
        binding.setBindingEvents(this);
        binding.setViewModel(viewModel);
        binding.appBar.setBackClickListener(this);
        binding.appBar.setTitle(getResources().getString(R.string.returns_verify_data_header));
        setUp();
    }

    private void setUp() {
        if (getArguments() != null) {
            returnId = getArguments().getInt(KEY_RETURN_ID);
        }
    }

    @Override
    protected void setUpRecyclers() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        adapter = new SelectedReturnOrderItemsAdapter(viewModel);

        binding.returnOrderItemsListRv.setLayoutManager(linearLayoutManager);
        binding.returnOrderItemsListRv.setHasFixedSize(true);
        binding.returnOrderItemsListRv.setAdapter(adapter);
    }

    @Override
    protected void setUpObservers() {
        viewModel.getReturnsOrderLiveData().observe(getViewLifecycleOwner(), this::onLoadReturnById);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (savedInstanceState == null) {
            viewModel.init(returnId);
        }
    }

    private void onLoadReturnById(ReturnsOrder returnsOrder) {
        binding.setReturnsOrder(returnsOrder);
        adapter.setItems(returnsOrder.getReturnItemsList());
    }

    @Override
    public void goBack() {
        viewModel.onBackPressed();
    }

    @Override
    public void onNext() {
        viewModel.sendReturnOrder(returnId);
        if (getParentFragment() != null) {
            ((MainFragment) getParentFragment()).showBottomNavbar();
        }
    }
}
