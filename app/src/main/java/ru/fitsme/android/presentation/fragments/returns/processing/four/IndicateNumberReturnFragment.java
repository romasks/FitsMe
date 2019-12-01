package ru.fitsme.android.presentation.fragments.returns.processing.four;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import ru.fitsme.android.R;
import ru.fitsme.android.databinding.FragmentReturnIndicateNumberBinding;
import ru.fitsme.android.domain.interactors.returns.IReturnsInteractor;
import ru.fitsme.android.presentation.common.listener.BackClickListener;
import ru.fitsme.android.presentation.fragments.base.BaseFragment;
import ru.fitsme.android.presentation.fragments.base.ViewModelFactory;

public class IndicateNumberReturnFragment extends BaseFragment<IndicateNumberReturnViewModel> implements IndicateNumberReturnBindingEvents, BackClickListener {

    @Inject
    IReturnsInteractor returnsInteractor;

    private static final String KEY_RETURN_ID = "RETURN_ID";

    private FragmentReturnIndicateNumberBinding binding;
    private int returnId;

    public static IndicateNumberReturnFragment newInstance(int returnId) {
        Bundle args = new Bundle();
        args.putInt(KEY_RETURN_ID, returnId);
        IndicateNumberReturnFragment fragment = new IndicateNumberReturnFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_return_indicate_number, container, false);
        binding.setBindingEvents(this);
        binding.appBar.setBackClickListener(this);
        binding.appBar.setTitle(getString(R.string.returns_indicate_number_header));
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getArguments() != null) {
            returnId = getArguments().getInt(KEY_RETURN_ID);
        }

        viewModel = ViewModelProviders.of(this,
                new ViewModelFactory(returnsInteractor)).get(IndicateNumberReturnViewModel.class);
        if (savedInstanceState == null) {
            viewModel.init();
        }
        binding.setViewModel(viewModel);
    }

    @Override
    public void goBack() {
        viewModel.backToReturnsChooseItems();
    }

    @Override
    public void onNext() {
        if (binding.indicateNumber.length() < 13) {
            Toast.makeText(getContext(), R.string.warning_indicate_number_is_not_filled, Toast.LENGTH_SHORT).show();
        } else {
            viewModel.goToReturnsBillingInfo(String.valueOf(binding.indicateNumber.getText()), returnId);
        }
    }
}
