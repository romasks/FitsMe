package ru.fitsme.android.presentation.fragments.cart.buttonstate;

import android.annotation.SuppressLint;

import ru.fitsme.android.R;
import ru.fitsme.android.app.App;
import ru.fitsme.android.databinding.FragmentCartBinding;
import ru.fitsme.android.presentation.fragments.cart.CartFragment;
import ru.fitsme.android.presentation.fragments.cart.CartViewModel;

public class NormalState extends ButtonState {
    public NormalState(FragmentCartBinding binding, CartFragment callback) {
        super(binding, callback);
        this.binding.cartProceedToCheckoutBtn.setAlpha(1f);
        this.binding.cartProceedToCheckoutBtn.setBackgroundResource(R.drawable.bg_to_cart_btn);
        this.binding.cartProceedToCheckoutBtn.setEnabled(true);
        this.binding.cartProceedToCheckoutBtn.setText(R.string.cart_proceed_to_checkout_btn_text);
        this.binding.cartProceedToCheckoutBtn.setTextColor(App.getInstance().getResources().getColor(R.color.white));
    }

    @SuppressLint("CheckResult")
    @Override
    public void onButtonClick(CartViewModel viewModel, CartFragment handler) {
        viewModel.goToCheckout();
    }
}
