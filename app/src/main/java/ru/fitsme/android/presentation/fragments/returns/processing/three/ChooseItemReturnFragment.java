package ru.fitsme.android.presentation.fragments.returns.processing.three;

import android.arch.lifecycle.ViewModelProviders;
import android.arch.paging.PagedList;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;

import ru.fitsme.android.R;
import ru.fitsme.android.databinding.FragmentReturnChooseItemBinding;
import ru.fitsme.android.domain.entities.returns.ReturnsItem;
import ru.fitsme.android.domain.interactors.returns.IReturnsInteractor;
import ru.fitsme.android.presentation.fragments.base.BaseFragment;
import ru.fitsme.android.presentation.fragments.base.ViewModelFactory;

public class ChooseItemReturnFragment extends BaseFragment<ChooseItemReturnViewModel> implements ChooseItemReturnBindingEvents {

    @Inject
    IReturnsInteractor returnsInteractor;

    public static final String KEY_POSITION = "POSITION";

    private FragmentReturnChooseItemBinding binding;
    private ReturnOrderItemsAdapter adapter;
    private int orderPosition;

    public static ChooseItemReturnFragment newInstance(int position) {
        Bundle args = new Bundle();
        args.putInt(KEY_POSITION, position);
        ChooseItemReturnFragment fragment = new ChooseItemReturnFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_return_choose_item, container, false);
        binding.setBindingEvents(this);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getArguments() != null) {
            orderPosition = getArguments().getInt(KEY_POSITION, 0);
        }

        viewModel = ViewModelProviders.of(this,
                new ViewModelFactory(returnsInteractor)).get(ChooseItemReturnViewModel.class);
        if (savedInstanceState == null) {
            viewModel.init();
        }
        binding.setViewModel(viewModel);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        adapter = new ReturnOrderItemsAdapter(viewModel);

        binding.returnOrderItemsListRv.setLayoutManager(linearLayoutManager);
        binding.returnOrderItemsListRv.setHasFixedSize(true);
        binding.returnOrderItemsListRv.setAdapter(adapter);

        viewModel.getPageLiveData().observe(this, this::onLoadPage);
    }

    private void onLoadPage(PagedList<ReturnsItem> pagedList) {
        adapter.setItems(viewModel.clothesList);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void goBack() {
        viewModel.backToReturnsChooseOrder();
    }

    @Override
    public void onNext() {
        viewModel.goToReturnsIndicateNumber();
    }
}
