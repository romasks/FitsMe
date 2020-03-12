package ru.fitsme.android.presentation.fragments.returns.processing.four;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import ru.fitsme.android.R;
import ru.fitsme.android.databinding.FragmentReturnIndicateNumberBinding;
import ru.fitsme.android.domain.entities.returns.ReturnsOrder;
import ru.fitsme.android.presentation.common.listener.BackClickListener;
import ru.fitsme.android.presentation.fragments.base.BaseFragment;

public class IndicateNumberReturnFragment extends BaseFragment<IndicateNumberReturnViewModel> implements IndicateNumberReturnBindingEvents, BackClickListener {

    private FragmentReturnIndicateNumberBinding binding;

    private static final String KEY_RETURN_ID = "RETURN_ID";
    private int returnId = 0;

    public static IndicateNumberReturnFragment newInstance(int returnId) {
        Bundle args = new Bundle();
        args.putInt(KEY_RETURN_ID, returnId);
        IndicateNumberReturnFragment fragment = new IndicateNumberReturnFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_return_indicate_number;
    }

    @Override
    protected void afterCreateView(View view) {
        binding = FragmentReturnIndicateNumberBinding.bind(view);
        binding.setBindingEvents(this);
        binding.setViewModel(viewModel);
        binding.appBar.setBackClickListener(this);
        binding.appBar.setTitle(getString(R.string.returns_indicate_number_header));
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        if (getArguments() != null) {
            returnId = getArguments().getInt(KEY_RETURN_ID);
        }
        if (savedInstanceState == null) {
            viewModel.init(returnId);
        }
    }

    @Override
    protected void setUpObservers() {
        viewModel.getReturnsOrderLiveData().observe(getViewLifecycleOwner(), this::onLoadReturnsOrder);
    }

    @Override
    public void goBack() {
        viewModel.onBackPressed();
    }

    @Override
    public void onNext() {
        if (binding.indicateNumber.length() < 13) {
            Toast.makeText(getContext(), R.string.warning_indicate_number_is_not_filled, Toast.LENGTH_SHORT).show();
        } else {
            viewModel.goToReturnsBillingInfo(returnId, String.valueOf(binding.indicateNumber.getText()));
        }
    }

    private void onLoadReturnsOrder(ReturnsOrder returnsOrder) {
        if (returnsOrder.getDeliveryDetails() != null) {
            binding.indicateNumber.setText(returnsOrder.getDeliveryDetails());
        }
    }
}
