package ru.fitsme.android.presentation.fragments.returns.processing.two;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;

import ru.fitsme.android.R;
import ru.fitsme.android.databinding.FragmentReturnChooseOrderBinding;
import ru.fitsme.android.domain.entities.returns.ReturnsItem;
import ru.fitsme.android.domain.interactors.returns.IReturnsInteractor;
import ru.fitsme.android.presentation.fragments.base.BaseFragment;
import ru.fitsme.android.presentation.fragments.base.ViewModelFactory;

public class ChooseOrderReturnFragment extends BaseFragment<ChooseOrderReturnViewModel> implements ChooseOrderReturnBindingEvents {

    @Inject
    IReturnsInteractor returnsInteractor;

    private FragmentReturnChooseOrderBinding binding;
    private ReturnOrdersAdapter adapter;

    static DiffUtil.ItemCallback<ReturnsItem> DIFF_CALLBACK = new DiffUtil.ItemCallback<ReturnsItem>() {

        @Override
        public boolean areItemsTheSame(@NonNull ReturnsItem oldItem, @NonNull ReturnsItem newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull ReturnsItem oldItem, @NonNull ReturnsItem newItem) {
            return oldItem.equals(newItem);
        }
    };

    public static ChooseOrderReturnFragment newInstance() {
        return new ChooseOrderReturnFragment();
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_return_choose_order, container, false);
        binding.setBindingEvents(this);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = ViewModelProviders.of(this,
                new ViewModelFactory(returnsInteractor)).get(ChooseOrderReturnViewModel.class);
        if (savedInstanceState == null) {
            viewModel.init();
        }
        binding.setViewModel(viewModel);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        adapter = new ReturnOrdersAdapter(viewModel);

        binding.returnOrdersListRv.setLayoutManager(linearLayoutManager);
        binding.returnOrdersListRv.setHasFixedSize(true);
        binding.returnOrdersListRv.setAdapter(adapter);

        viewModel.getPageLiveData().observe(this, this::onLoadPage);
    }

    private void onLoadPage(PagedList<ReturnsItem> pagedList) {
        adapter.submitList(pagedList);
    }

    @Override
    public void goBack() {
        viewModel.backToReturnsHowTo();
    }

    @Override
    public void onBackPressed() {
        viewModel.onBackPressed();
    }
}
