package ru.fitsme.android.presentation.fragments.returns.processing.one;

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
import ru.fitsme.android.databinding.FragmentReturnHowToBinding;
import ru.fitsme.android.domain.interactors.returns.IReturnsInteractor;
import ru.fitsme.android.presentation.common.listener.BackClickListener;
import ru.fitsme.android.presentation.fragments.base.BaseFragment;
import ru.fitsme.android.presentation.fragments.base.ViewModelFactory;

public class HowToReturnFragment extends BaseFragment<HowToReturnViewModel> implements HowToReturnBindingEvents, BackClickListener {

    @Inject
    IReturnsInteractor returnsInteractor;

    private FragmentReturnHowToBinding binding;

    public static HowToReturnFragment newInstance() {
        return new HowToReturnFragment();
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_return_how_to, container, false);
        binding.setBindingEvents(this);
        binding.appBar.setBackClickListener(this);
        binding.appBar.setTitle(getString(R.string.returns_how_to_header));
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = ViewModelProviders.of(this,
                new ViewModelFactory(returnsInteractor)).get(HowToReturnViewModel.class);
        if (savedInstanceState == null) {
            viewModel.init();
        }
        binding.setViewModel(viewModel);
    }

    @Override
    public void goBack() {
        viewModel.backToOrdersReturn();
    }

    @Override
    public void onNext() {
        viewModel.goToReturnsChooseOrder();
    }

    @Override
    public void onBackPressed() {
        viewModel.onBackPressed();
    }
}
