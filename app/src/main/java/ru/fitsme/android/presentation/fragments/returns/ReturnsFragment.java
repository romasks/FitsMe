package ru.fitsme.android.presentation.fragments.returns;

import android.arch.lifecycle.ViewModelProviders;
import android.arch.paging.PagedList;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;

import ru.fitsme.android.R;
import ru.fitsme.android.databinding.FragmentReturnsBinding;
import ru.fitsme.android.domain.entities.returns.ReturnsItem;
import ru.fitsme.android.domain.interactors.returns.IReturnsInteractor;
import ru.fitsme.android.presentation.fragments.base.BaseFragment;
import ru.fitsme.android.presentation.fragments.base.ViewModelFactory;
import ru.fitsme.android.presentation.fragments.main.MainFragment;

public class ReturnsFragment extends BaseFragment<ReturnsViewModel> implements ReturnsBindingEvents {

    @Inject
    IReturnsInteractor returnsInteractor;

    private FragmentReturnsBinding binding;
    private ReturnsAdapter adapter;

    public static DiffUtil.ItemCallback<ReturnsItem> DIFF_CALLBACK = new DiffUtil.ItemCallback<ReturnsItem>() {

        @Override
        public boolean areItemsTheSame(@NonNull ReturnsItem oldItem, @NonNull ReturnsItem newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull ReturnsItem oldItem, @NonNull ReturnsItem newItem) {
            return oldItem.equals(newItem);
        }
    };

    public static ReturnsFragment newInstance() {
        return new ReturnsFragment();
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_returns, container, false);
        binding.setBindingEvents(this);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = ViewModelProviders.of(this,
                new ViewModelFactory(returnsInteractor)).get(ReturnsViewModel.class);
        if (savedInstanceState == null) {
            viewModel.init((MainFragment) getParentFragment());
        }
        binding.setViewModel(viewModel);

        adapter = new ReturnsAdapter(viewModel);

        binding.returnsListRv.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.returnsListRv.setHasFixedSize(true);
        binding.returnsListRv.setAdapter(adapter);

        viewModel.getPageLiveData().observe(this, this::onLoadPage);

        viewModel.getReturnsIsEmpty().observe(this, this::onReturnsIsEmpty);
    }

    private void onLoadPage(PagedList<ReturnsItem> pagedList) {
        adapter.submitList(pagedList);
    }

    private void onReturnsIsEmpty(Boolean b) {
        binding.returnsNoItemGroup.setVisibility(b ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onClickGoToCheckout() {
        ((MainFragment) getParentFragment()).goToCheckout();
    }

    @Override
    public void goBack() {
        ((MainFragment) getParentFragment()).goToMainProfile();
    }

    @Override
    public void goToCreatingNewReturn() {
        viewModel.goToReturnsHowTo();
    }
}
