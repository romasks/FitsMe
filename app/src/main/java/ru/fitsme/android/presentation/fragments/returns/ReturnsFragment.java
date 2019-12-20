package ru.fitsme.android.presentation.fragments.returns;

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
import ru.fitsme.android.databinding.FragmentReturnsBinding;
import ru.fitsme.android.domain.entities.returns.ReturnsItem;
import ru.fitsme.android.domain.entities.returns.ReturnsOrder;
import ru.fitsme.android.domain.interactors.returns.IReturnsInteractor;
import ru.fitsme.android.presentation.common.listener.BackClickListener;
import ru.fitsme.android.presentation.fragments.base.BaseFragment;
import ru.fitsme.android.presentation.fragments.base.ViewModelFactory;
import ru.fitsme.android.presentation.fragments.main.MainFragment;

public class ReturnsFragment extends BaseFragment<ReturnsViewModel> implements ReturnsBindingEvents, BackClickListener {

    @Inject
    IReturnsInteractor returnsInteractor;

    private FragmentReturnsBinding binding;
    private ReturnsAdapter adapter;

    static DiffUtil.ItemCallback<ReturnsOrder> DIFF_CALLBACK = new DiffUtil.ItemCallback<ReturnsOrder>() {

        @Override
        public boolean areItemsTheSame(@NonNull ReturnsOrder oldItem, @NonNull ReturnsOrder newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull ReturnsOrder oldItem, @NonNull ReturnsOrder newItem) {
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
        binding.appBar.setBackClickListener(this);
        binding.appBar.setTitle(getString(R.string.screen_title_orders_return));
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

        if (getParentFragment() != null) {
            ((MainFragment) getParentFragment()).showBottomNavigation(false);
        }

        adapter = new ReturnsAdapter(viewModel);

        binding.returnsListRv.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.returnsListRv.setHasFixedSize(true);
        binding.returnsListRv.setAdapter(adapter);

        viewModel.getPageLiveData().observe(getViewLifecycleOwner(), this::onLoadPage);

        viewModel.getReturnsIsEmpty().observe(getViewLifecycleOwner(), this::onReturnsIsEmpty);
    }

    private void onLoadPage(PagedList<ReturnsOrder> pagedList) {
        adapter.submitList(pagedList);
    }

    private void onReturnsIsEmpty(Boolean b) {
        binding.returnsNoItemGroup.setVisibility(b ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onClickGoToCheckout() {
        if (getParentFragment() != null) {
            ((MainFragment) getParentFragment()).goToCheckout();
        }
    }

    @Override
    public void goBack() {
        if (getParentFragment() != null) {
            ((MainFragment) getParentFragment()).goToMainProfile();
        }
    }

    @Override
    public void goToCreatingNewReturn() {
        viewModel.goToReturnsHowTo();
        if (getParentFragment() != null) {
            ((MainFragment) getParentFragment()).hideBottomNavbar();
        }
    }

    @Override
    public void onBackPressed() {
        viewModel.onBackPressed();
    }
}
