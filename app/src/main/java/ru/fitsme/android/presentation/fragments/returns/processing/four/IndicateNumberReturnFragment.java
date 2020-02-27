package ru.fitsme.android.presentation.fragments.returns.processing.four;

import android.view.View;
import android.widget.Toast;

import ru.fitsme.android.R;
import ru.fitsme.android.databinding.FragmentReturnIndicateNumberBinding;
import ru.fitsme.android.presentation.common.listener.BackClickListener;
import ru.fitsme.android.presentation.fragments.base.BaseFragment;

public class IndicateNumberReturnFragment extends BaseFragment<IndicateNumberReturnViewModel> implements IndicateNumberReturnBindingEvents, BackClickListener {

    private FragmentReturnIndicateNumberBinding binding;

    public static IndicateNumberReturnFragment newInstance() {
        return new IndicateNumberReturnFragment();
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
    public void goBack() {
        viewModel.onBackPressed();
    }

    @Override
    public void onNext() {
        if (binding.indicateNumber.length() < 13) {
            Toast.makeText(getContext(), R.string.warning_indicate_number_is_not_filled, Toast.LENGTH_SHORT).show();
        } else {
            viewModel.goToReturnsBillingInfo(String.valueOf(binding.indicateNumber.getText()));
        }
    }
}
