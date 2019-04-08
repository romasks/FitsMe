package ru.fitsme.android.presentation.fragments.checkout;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;

import ru.fitsme.android.R;
import ru.fitsme.android.app.App;
import ru.fitsme.android.databinding.FragmentCheckoutBinding;
import ru.fitsme.android.domain.entities.order.Order;
import ru.fitsme.android.domain.interactors.orders.IOrdersInteractor;

import static ru.fitsme.android.utils.Constants.GONE;
import static ru.fitsme.android.utils.Constants.VISIBLE;

public class CheckoutFragment extends Fragment {
    @Inject
    IOrdersInteractor ordersInteractor;

    private FragmentCheckoutBinding binding;
    private CheckoutViewModel viewModel;

    public CheckoutFragment() {
        App.getInstance().getDi().inject(this);
    }

    public static CheckoutFragment newInstance() {
        return new CheckoutFragment();
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_checkout, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = ViewModelProviders.of(this,
                new CheckoutViewModel.Factory(ordersInteractor)).get(CheckoutViewModel.class);
        if (savedInstanceState == null) {
            viewModel.init();
        }

        viewModel.loading.set(VISIBLE);
        viewModel.getOrderLiveData().observe(this, this::onLoadOrder);
    }

    private void onLoadOrder(Order order) {
        viewModel.loading.set(GONE);
    }
}
