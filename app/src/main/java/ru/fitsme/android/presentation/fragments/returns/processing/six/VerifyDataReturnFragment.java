package ru.fitsme.android.presentation.fragments.returns.processing.six;

import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import ru.fitsme.android.R;
import ru.fitsme.android.databinding.FragmentReturnVerifyDataBinding;
import ru.fitsme.android.domain.entities.returns.ReturnsOrder;
import ru.fitsme.android.presentation.common.listener.BackClickListener;
import ru.fitsme.android.presentation.fragments.base.BaseFragment;
import ru.fitsme.android.presentation.fragments.main.MainFragment;

public class VerifyDataReturnFragment extends BaseFragment<VerifyDataReturnViewModel> implements VerifyDataReturnBindingEvents, BackClickListener {

    private FragmentReturnVerifyDataBinding binding;
    private SelectedReturnOrderItemsAdapter adapter;

    public static VerifyDataReturnFragment newInstance() {
        return new VerifyDataReturnFragment();
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
        viewModel.sendReturnOrder();
        if (getParentFragment() != null) {
            ((MainFragment) getParentFragment()).showBottomNavbar();
        }
    }
}
