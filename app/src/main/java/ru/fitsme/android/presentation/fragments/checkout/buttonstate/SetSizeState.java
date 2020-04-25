package ru.fitsme.android.presentation.fragments.checkout.buttonstate;

import android.annotation.SuppressLint;

import ru.fitsme.android.R;
import ru.fitsme.android.app.App;
import ru.fitsme.android.databinding.FragmentCheckoutBinding;
import ru.fitsme.android.presentation.fragments.checkout.CheckoutFragment;
import ru.fitsme.android.presentation.fragments.checkout.CheckoutViewModel;

public class SetSizeState extends ButtonState {
    public SetSizeState(FragmentCheckoutBinding binding, CheckoutFragment callback) {
        super(binding, callback);
        this.binding.btnNext.setAlpha(1f);
        this.binding.btnNext.setBackgroundResource(R.drawable.bg_to_cart_btn);
        this.binding.btnNext.setEnabled(true);
        this.binding.btnNext.setText(R.string.set_your_size);
        this.binding.btnNext.setTextColor(App.getInstance().getResources().getColor(R.color.white));
    }

    @SuppressLint("CheckResult")
    @Override
    public void onButtonClick(CheckoutViewModel viewModel, CheckoutFragment handler) {
        handler.setSizeInProfile();
    }
}
