package ru.fitsme.android.presentation.fragments.returns.processing.first;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;

import ru.fitsme.android.R;
import ru.fitsme.android.databinding.FragmentHowToReturnBinding;
import ru.fitsme.android.domain.interactors.returns.favourites.IReturnsInteractor;
import ru.fitsme.android.presentation.fragments.base.BaseFragment;
import ru.fitsme.android.presentation.fragments.base.ViewModelFactory;

public class HowToReturnFragment extends BaseFragment<HowToReturnViewModel> implements HowToReturnBindingEvents {

    @Inject
    IReturnsInteractor returnsInteractor;

    private FragmentHowToReturnBinding binding;

    public static HowToReturnFragment newInstance() {
        return new HowToReturnFragment();
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_how_to_return, container, false);
        binding.setBindingEvents(this);
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
}
