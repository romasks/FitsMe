package ru.fitsme.android.presentation.fragments.returns.processing.five;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;

import ru.fitsme.android.R;
import ru.fitsme.android.databinding.FragmentReturnBillingInfoBinding;
import ru.fitsme.android.domain.interactors.returns.IReturnsInteractor;
import ru.fitsme.android.presentation.fragments.base.BaseFragment;
import ru.fitsme.android.presentation.fragments.base.ViewModelFactory;

public class BillingInfoReturnFragment extends BaseFragment<BillingInfoReturnViewModel> implements BillingInfoReturnBindingEvents {

    @Inject
    IReturnsInteractor returnsInteractor;

    private FragmentReturnBillingInfoBinding binding;

    public static BillingInfoReturnFragment newInstance() {
        return new BillingInfoReturnFragment();
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_return_billing_info, container, false);
        binding.setBindingEvents(this);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = ViewModelProviders.of(this,
                new ViewModelFactory(returnsInteractor)).get(BillingInfoReturnViewModel.class);
        if (savedInstanceState == null) {
            viewModel.init();
        }
        binding.setViewModel(viewModel);
    }

    @Override
    public void goBack() {
        viewModel.backToReturnsIndicateNumber();
    }

    @Override
    public void onNext() {
        viewModel.goToReturnsVerifyData();
    }

    @Override
    public void onBackPressed() {

    }
}
