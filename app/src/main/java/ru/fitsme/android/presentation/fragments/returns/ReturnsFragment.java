package ru.fitsme.android.presentation.fragments.returns;

import android.view.View;

import androidx.paging.PagedList;
import androidx.recyclerview.widget.LinearLayoutManager;

import ru.fitsme.android.R;
import ru.fitsme.android.databinding.FragmentReturnsBinding;
import ru.fitsme.android.domain.entities.returns.ReturnsOrder;
import ru.fitsme.android.presentation.common.listener.BackClickListener;
import ru.fitsme.android.presentation.fragments.base.BaseFragment;
import ru.fitsme.android.presentation.fragments.main.MainFragment;

public class ReturnsFragment extends BaseFragment<ReturnsViewModel> implements ReturnsBindingEvents, BackClickListener {

    private FragmentReturnsBinding binding;
    private ReturnsAdapter adapter;

    public static ReturnsFragment newInstance() {
        return new ReturnsFragment();
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_returns;
    }

    @Override
    protected void afterCreateView(View view) {
        binding = FragmentReturnsBinding.bind(view);
        binding.setBindingEvents(this);
        binding.setViewModel(viewModel);
        binding.appBar.setBackClickListener(this);
        binding.appBar.setTitle(getString(R.string.screen_title_orders_return));
        setUp();
    }

    private void setUp() {
        if (getParentFragment() != null) {
            ((MainFragment) getParentFragment()).showBottomNavigation(false);
        }
    }

    @Override
    protected void setUpRecyclers() {
        adapter = new ReturnsAdapter(viewModel);

        binding.returnsListRv.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.returnsListRv.setHasFixedSize(true);
        binding.returnsListRv.setAdapter(adapter);
    }

    @Override
    protected void setUpObservers() {
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
        viewModel.goToCheckout();
    }

    @Override
    public void goBack() {
        viewModel.onBackPressed();
    }

    @Override
    public void goToCreatingNewReturn() {
        viewModel.goToReturnsHowTo();
        if (getParentFragment() != null) {
            ((MainFragment) getParentFragment()).hideBottomNavbar();
        }
    }
}
