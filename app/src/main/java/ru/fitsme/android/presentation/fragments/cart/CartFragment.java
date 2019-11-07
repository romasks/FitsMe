package ru.fitsme.android.presentation.fragments.cart;

import android.annotation.SuppressLint;
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
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

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

public class CartFragment extends BaseFragment<CartViewModel>
        implements CartBindingEvents, CartRecyclerItemTouchHelper.RecyclerItemTouchHelperListener {

    @Inject
    IOrdersInteractor ordersInteractor;

    private FragmentCartBinding binding;
    private CartAdapter adapter;

    static DiffUtil.ItemCallback<OrderItem> DIFF_CALLBACK = new DiffUtil.ItemCallback<OrderItem>() {
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

        adapter = new CartAdapter(viewModel);

        binding.cartListRv.setHasFixedSize(true);
        binding.cartListRv.setAdapter(adapter);

        viewModel.getPageLiveData().observe(
                this, this::onLoadPage);

        viewModel.getCartIsEmpty().observe(this, this::onCartIsEmpty);

        ItemTouchHelper.SimpleCallback simpleCallback =
                new CartRecyclerItemTouchHelper(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT, this);
        new ItemTouchHelper(simpleCallback).attachToRecyclerView(binding.cartListRv);
    }

    private void onLoadPage(PagedList<OrderItem> pagedList) {
        if (getParentFragment() != null) {
            if (pagedList == null || pagedList.size() == 0) {
                ((MainFragment) getParentFragment()).showBottomShadow(true);
            } else {
                ((MainFragment) getParentFragment()).showBottomShadow(false);
            }
        }
        adapter.submitList(pagedList);
    }

    private void onCartIsEmpty(Boolean b) {
        if (b) {
            binding.cartNoItemGroup.setVisibility(View.VISIBLE);
            binding.cartProceedToCheckoutGroup.setVisibility(View.GONE);
        } else {
            binding.cartNoItemGroup.setVisibility(View.GONE);
            binding.cartProceedToCheckoutGroup.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onClickGoToCheckout() {
        if (getParentFragment() != null) {
            ((MainFragment) getParentFragment()).goToCheckout();
        }
    }

    @Override
    public void onClickGoToFavourites() {
        if (getParentFragment() != null) {
            ((MainFragment) getParentFragment()).goToFavourites();
        }
    }

    @Override
    public void onClickGoToRateItems() {
        if (getParentFragment() != null) {
            ((MainFragment) getParentFragment()).goToRateItems();
        }
    }

    @Override
    public void onDestroyView() {
        if (getParentFragment() != null) {
            ((MainFragment) getParentFragment()).showBottomShadow(true);
        }
        super.onDestroyView();
    }

    @SuppressLint("CheckResult")
    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
        if (position != RecyclerView.NO_POSITION) {
            viewModel.removeItemFromOrder(position)
                    .subscribe(removedOrderItem -> {
                        if (removedOrderItem.getId() != 0) {
                            adapter.notifyItemChanged(position);
                        }
                    }, Timber::e);
        }
    }
}
