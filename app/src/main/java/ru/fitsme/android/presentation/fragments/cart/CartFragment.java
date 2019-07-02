package ru.fitsme.android.presentation.fragments.cart;

import android.arch.lifecycle.ViewModelProviders;
import android.arch.paging.PagedList;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import javax.inject.Inject;

import ru.fitsme.android.R;
import ru.fitsme.android.app.App;
import ru.fitsme.android.databinding.FragmentCartBinding;
import ru.fitsme.android.domain.entities.order.OrderItem;
import ru.fitsme.android.domain.interactors.orders.IOrdersInteractor;
import ru.fitsme.android.presentation.fragments.base.BaseFragment;
import ru.fitsme.android.presentation.fragments.base.ViewModelFactory;
import ru.fitsme.android.presentation.fragments.main.MainFragment;
import timber.log.Timber;

import static ru.fitsme.android.utils.Constants.GONE;
import static ru.fitsme.android.utils.Constants.VISIBLE;

public class CartFragment extends BaseFragment<CartViewModel> implements CartBindingEvents {

    @Inject
    IOrdersInteractor ordersInteractor;

    private FragmentCartBinding binding;
    private CartAdapter adapter;

    public static DiffUtil.ItemCallback<OrderItem> DIFF_CALLBACK = new DiffUtil.ItemCallback<OrderItem>() {
        @Override
        public boolean areItemsTheSame(@NonNull OrderItem oldItem, @NonNull OrderItem newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull OrderItem oldItem, @NonNull OrderItem newItem) {
            return oldItem.equals(newItem);
        }
    };

    public CartFragment() {
        App.getInstance().getDi().inject(this);
    }

    public static CartFragment newInstance() {
        return new CartFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_cart, container, false);
        binding.setBindingEvents(this);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = ViewModelProviders.of(this,
                new ViewModelFactory(ordersInteractor)).get(CartViewModel.class);
        if (savedInstanceState == null) {
            viewModel.init();
        }
        binding.setViewModel(viewModel);

        adapter = new CartAdapter();

        binding.cartListRv.setHasFixedSize(true);
        binding.cartListRv.setAdapter(adapter);

        viewModel.loading.set(VISIBLE);
        viewModel.showEmpty.set(VISIBLE);

        viewModel.getPageLiveData().observe(
                this, this::onLoadPage);
    }

    private void onLoadPage(PagedList<OrderItem> pagedList) {
        viewModel.loading.set(GONE);
        if (pagedList == null || pagedList.size() == 0) {
            viewModel.showEmpty.set(VISIBLE);
        } else {
            viewModel.showEmpty.set(GONE);
            viewModel.setTotalPrice(pagedList);
        }
        adapter.submitList(pagedList);
    }

    @Override
    public void onClickGoToCheckout() {
        ((MainFragment) getParentFragment()).goToCheckout();
    }

    @Override
    public void onClickGoToFavourites() {
        ((MainFragment) getParentFragment()).goToFavourites();
    }
}
