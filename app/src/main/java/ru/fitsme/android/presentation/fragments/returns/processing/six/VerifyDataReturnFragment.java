package ru.fitsme.android.presentation.fragments.returns.processing.six;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import ru.fitsme.android.R;
import ru.fitsme.android.databinding.FragmentReturnVerifyDataBinding;
import ru.fitsme.android.domain.entities.returns.ReturnsItem;
import ru.fitsme.android.domain.interactors.returns.IReturnsInteractor;
import ru.fitsme.android.presentation.common.listener.BackClickListener;
import ru.fitsme.android.presentation.fragments.base.BaseFragment;
import ru.fitsme.android.presentation.fragments.base.ViewModelFactory;
import ru.fitsme.android.presentation.fragments.main.MainFragment;

public class VerifyDataReturnFragment extends BaseFragment<VerifyDataReturnViewModel> implements VerifyDataReturnBindingEvents, BackClickListener {

    @Inject
    IReturnsInteractor returnsInteractor;

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
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_return_verify_data, container, false);
        binding.setBindingEvents(this);
        binding.appBar.setBackClickListener(this);
        binding.appBar.setTitle(getResources().getString(R.string.returns_verify_data_header));
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getArguments() != null) {
            returnId = getArguments().getInt(KEY_RETURN_ID);
//            binding.setReturnsItem(returnsItem);
        }

        viewModel = ViewModelProviders.of(this,
                new ViewModelFactory(returnsInteractor)).get(VerifyDataReturnViewModel.class);
        if (savedInstanceState == null) {
            viewModel.init(returnId);
        }
        binding.setViewModel(viewModel);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        adapter = new SelectedReturnOrderItemsAdapter(viewModel);

        binding.returnOrderItemsListRv.setLayoutManager(linearLayoutManager);
        binding.returnOrderItemsListRv.setHasFixedSize(true);
        binding.returnOrderItemsListRv.setAdapter(adapter);

//        adapter.setItems(returnsItem.getItems());
    }

    @Override
    public void goBack() {
        viewModel.backToReturnsBillingInfo();
    }

    @Override
    public void onNext() {
//        viewModel.sendReturnOrder(returnsItem);
        if (getParentFragment() != null) {
            ((MainFragment) getParentFragment()).showBottomNavbar();
        }
    }
}
