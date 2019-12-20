package ru.fitsme.android.presentation.fragments.returns.details;

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

import javax.inject.Inject;

import ru.fitsme.android.R;
import ru.fitsme.android.databinding.FragmentReturnDetailsBinding;
import ru.fitsme.android.domain.entities.returns.ReturnsOrder;
import ru.fitsme.android.domain.interactors.returns.IReturnsInteractor;
import ru.fitsme.android.presentation.common.listener.BackClickListener;
import ru.fitsme.android.presentation.fragments.base.BaseFragment;
import ru.fitsme.android.presentation.fragments.base.ViewModelFactory;

public class ReturnDetailsFragment extends BaseFragment<ReturnDetailsViewModel> implements ReturnDetailsBindingEvents, BackClickListener {

    @Inject
    IReturnsInteractor returnsInteractor;

    private static final String KEY_RETURN_ID = "RETURN_ID";

    private FragmentReturnDetailsBinding binding;
    private ReturnDetailsAdapter adapter;
    private int returnId;

    public static ReturnDetailsFragment newInstance(int returnId) {
        Bundle args = new Bundle();
        args.putInt(KEY_RETURN_ID, returnId);
        ReturnDetailsFragment fragment = new ReturnDetailsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_return_details, container, false);
        binding.setBindingEvents(this);
        binding.appBar.setBackClickListener(this);
        binding.appBar.setTitle(getResources().getString(R.string.returns_details_header));
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getArguments() != null) {
            returnId = getArguments().getInt(KEY_RETURN_ID);
        }

        viewModel = ViewModelProviders.of(this,
                new ViewModelFactory(returnsInteractor)).get(ReturnDetailsViewModel.class);
        if (savedInstanceState == null) {
            viewModel.init(returnId);
        }
        binding.setViewModel(viewModel);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        adapter = new ReturnDetailsAdapter(viewModel);

        binding.returnOrderItemsListRv.setLayoutManager(linearLayoutManager);
        binding.returnOrderItemsListRv.setHasFixedSize(true);
        binding.returnOrderItemsListRv.setAdapter(adapter);

        viewModel.getReturnsOrderLiveData().observe(getViewLifecycleOwner(), this::onLoadReturnById);
    }

    private void onLoadReturnById(ReturnsOrder returnsOrder) {
        binding.setReturnsOrder(returnsOrder);
        adapter.setItems(returnsOrder.getReturnItemsList());
    }

    @Override
    public void onBackPressed() {
        viewModel.onBackPressed();
    }

    @Override
    public void goBack() {
        viewModel.onBackPressed();
    }

    @Override
    public void onContinueMakeReturn() {

    }
}
