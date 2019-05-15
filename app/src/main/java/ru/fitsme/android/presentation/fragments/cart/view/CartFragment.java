package ru.fitsme.android.presentation.fragments.cart.view;

import android.arch.lifecycle.ViewModelProviders;
import android.arch.paging.PagedList;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.Group;
import android.support.v4.app.Fragment;
import android.support.v4.widget.ContentLoadingProgressBar;
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
import ru.fitsme.android.presentation.fragments.checkout.CheckoutFragment;
import ru.fitsme.android.presentation.fragments.favourites.view.FavouritesFragment;
import ru.fitsme.android.presentation.fragments.main.view.MainFragment;
import ru.fitsme.android.presentation.fragments.rateitems.view.RateItemsFragment;
import timber.log.Timber;


public class CartFragment extends Fragment {

    @Inject
    IOrdersInteractor ordersInteractor;

    private CartViewModel viewModel;
    private FragmentCartBinding binding;
    private CartAdapter adapter;

    private ContentLoadingProgressBar loadingProgressBar;
    private Group emptyGroup;
    private View proceedToCheckout;

    public static  DiffUtil.ItemCallback<OrderItem> DIFF_CALLBACK = new DiffUtil.ItemCallback<OrderItem>() {
        @Override
        public boolean areItemsTheSame(@NonNull OrderItem oldItem, @NonNull OrderItem newItem) {
            Timber.d("areItemsTheSame()");
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
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        loadingProgressBar = getView().findViewById(R.id.cart_loading_spinner);
        emptyGroup = getView().findViewById(R.id.cart_no_item_group);
        proceedToCheckout = getView().findViewById(R.id.cart_proceed_to_checkout_group);

        viewModel = ViewModelProviders.of(this,
                new CartViewModel.Factory(ordersInteractor)).get(CartViewModel.class);
        if (savedInstanceState == null) {
            viewModel.init();
        }

        adapter = new CartAdapter(viewModel);

        binding.cartListRv.setHasFixedSize(true);
        binding.cartListRv.setAdapter(adapter);

        loadingProgressBar.setVisibility(View.VISIBLE);
        viewModel.getPageLiveData().observe(
                this, this::onLoadPage);

        setListeners();
    }

    private void onLoadPage(PagedList<OrderItem> pagedList) {
        loadingProgressBar.setVisibility(View.GONE);
        if (pagedList == null || pagedList.size() == 0) {
            proceedToCheckout.setVisibility(View.INVISIBLE);
            emptyGroup.setVisibility(View.VISIBLE);
        } else {
            emptyGroup.setVisibility(View.INVISIBLE);
            proceedToCheckout.setVisibility(View.VISIBLE);
        }
        adapter.submitList(pagedList);
    }

    private void setListeners(){
        getView().findViewById(R.id.cart_proceed_to_checkout_btn).setOnClickListener( v -> {
            getParentFragment().getChildFragmentManager().beginTransaction()
                    .replace(R.id.container, CheckoutFragment.newInstance())
                    .commit();
        });

        getView().findViewById(R.id.cart_no_item_go_to_favourite_btn).setOnClickListener(v -> {
            getParentFragment().getChildFragmentManager().beginTransaction()
                    .replace(R.id.container, FavouritesFragment.newInstance())
                    .commit();
            ((MainFragment) getParentFragment()).getBottomNavView().setSelectedItemId(R.id.action_likes);
        });

        getView().findViewById(R.id.cart_no_item_go_to_rate_item_btn).setOnClickListener(v -> {
            getParentFragment().getChildFragmentManager().beginTransaction()
                    .replace(R.id.container, RateItemsFragment.newInstance())
                    .commit();
            ((MainFragment) getParentFragment()).getBottomNavView().setSelectedItemId(R.id.action_items);
        });

        getView().findViewById(R.id.cart_proceed_to_checkout_user_agreement_ll).setOnClickListener(v -> {
            Timber.d("Proceed to checkout user agreement was clicked");
            // TODO: 09.05.2019
        });
    }
}
