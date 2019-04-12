package ru.fitsme.android.presentation.fragments.cart.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ru.fitsme.android.R;
import ru.fitsme.android.presentation.fragments.checkout.CheckoutFragment;


public class CartFragment extends Fragment {

    public CartFragment() {
    }

    public static CartFragment newInstance() {
        return new CartFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_cart, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.cart_proceed_to_checkout_btn).setOnClickListener( v -> {
            getParentFragment().getChildFragmentManager().beginTransaction()
                    .replace(R.id.container, CheckoutFragment.newInstance())
                    .commit();
        });
    }
}
