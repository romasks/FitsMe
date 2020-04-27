package ru.fitsme.android.presentation.fragments.cart.buttonstate;

import android.annotation.SuppressLint;

import ru.fitsme.android.R;
import ru.fitsme.android.app.App;
import ru.fitsme.android.databinding.FragmentCartBinding;
import ru.fitsme.android.presentation.fragments.cart.CartFragment;
import ru.fitsme.android.presentation.fragments.cart.CartViewModel;

public class RemoveNoMatchSizeState extends ButtonState {
    public RemoveNoMatchSizeState(FragmentCartBinding binding, CartFragment callback) {
        super(binding, callback);
        this.binding.cartProceedToCheckoutBtn.setAlpha(1f);
        this.binding.cartProceedToCheckoutBtn.setBackgroundResource(R.drawable.bg_to_cart_btn);
        this.binding.cartProceedToCheckoutBtn.setEnabled(true);
        this.binding.cartProceedToCheckoutBtn.setText(R.string.cart_remove_no_match_size_btn_text);
        this.binding.cartProceedToCheckoutBtn.setTextColor(App.getInstance().getResources().getColor(R.color.white));
    }

    @SuppressLint("CheckResult")
    @Override
    public void onButtonClick(CartViewModel viewModel, CartFragment handler) {
        handler.removeNoSizeItems();
    }
}
