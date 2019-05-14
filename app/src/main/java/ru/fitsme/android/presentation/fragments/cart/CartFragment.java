package ru.fitsme.android.presentation.fragments.cart;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import javax.inject.Inject;

import ru.fitsme.android.R;
import ru.fitsme.android.databinding.FragmentCartBinding;
import ru.fitsme.android.domain.interactors.orders.IOrdersInteractor;
import ru.fitsme.android.presentation.fragments.base.BaseFragment;
import ru.fitsme.android.presentation.fragments.base.ViewModelFactory;
import ru.fitsme.android.presentation.fragments.checkout.CheckoutFragment;
import ru.fitsme.android.presentation.fragments.main.MainFragment;

import static ru.fitsme.android.utils.Constants.VISIBLE;

public class CartFragment extends BaseFragment<CartViewModel> implements CartBindingEvents {

    @Inject
    IOrdersInteractor ordersInteractor;

    private FragmentCartBinding binding;

    public static CartFragment newInstance() {
        return new CartFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_cart, container, false);
        binding.setBindingEvents(this);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = ViewModelProviders.of(this, new ViewModelFactory(ordersInteractor)).get(CartViewModel.class);
        if (savedInstanceState == null) {
            viewModel.init();
            viewModel.setAdapter(R.layout.item_cart);
        }
        binding.setViewModel(viewModel);

        binding.cartListRv.setHasFixedSize(true);
        binding.cartListRv.setAdapter(viewModel.getAdapter());

        viewModel.loading.set(VISIBLE);
        viewModel.showEmpty.set(VISIBLE);
    }

    @Override
    public void onClickGoToCheckout() {
        getParentFragment().getChildFragmentManager().beginTransaction()
                .replace(R.id.container, CheckoutFragment.newInstance())
                .commit();
    }

    @Override
    public void onClickGoToFavourites() {
        ((MainFragment) getParentFragment()).goToFavourites();
    }
}
