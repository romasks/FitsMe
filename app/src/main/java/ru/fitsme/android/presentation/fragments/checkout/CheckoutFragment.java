package ru.fitsme.android.presentation.fragments.checkout;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.jetbrains.annotations.NotNull;

import ru.fitsme.android.R;
import ru.fitsme.android.app.App;
import ru.fitsme.android.databinding.FragmentCheckoutBinding;

public class CheckoutFragment extends Fragment {

    private FragmentCheckoutBinding binding;

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

}
