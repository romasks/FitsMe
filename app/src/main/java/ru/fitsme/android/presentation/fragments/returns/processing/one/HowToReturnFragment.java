package ru.fitsme.android.presentation.fragments.returns.processing.one;

import android.view.View;

import ru.fitsme.android.R;
import ru.fitsme.android.databinding.FragmentReturnHowToBinding;
import ru.fitsme.android.presentation.common.listener.BackClickListener;
import ru.fitsme.android.presentation.fragments.base.BaseFragment;

public class HowToReturnFragment extends BaseFragment<HowToReturnViewModel> implements HowToReturnBindingEvents, BackClickListener {

    private FragmentReturnHowToBinding binding;

    public static HowToReturnFragment newInstance() {
        return new HowToReturnFragment();
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_return_how_to;
    }

    @Override
    protected void afterCreateView(View view) {
        binding = FragmentReturnHowToBinding.bind(view);
        binding.setBindingEvents(this);
        binding.setViewModel(viewModel);
        binding.appBar.setBackClickListener(this);
        binding.appBar.setTitle(getString(R.string.returns_how_to_header));
    }

    @Override
    public void goBack() {
        viewModel.onBackPressed();
    }

    @Override
    public void onNext() {
        viewModel.goToReturnsChooseOrder();
    }
}
