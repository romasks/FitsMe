package ru.fitsme.android.presentation.fragments.checkout.buttonstate;

import ru.fitsme.android.databinding.FragmentCheckoutBinding;
import ru.fitsme.android.presentation.fragments.checkout.CheckoutFragment;
import ru.fitsme.android.presentation.fragments.checkout.CheckoutViewModel;

public abstract class ButtonState {
    FragmentCheckoutBinding binding;
    CheckoutFragment callback;

    ButtonState(FragmentCheckoutBinding binding, CheckoutFragment callback) {
        this.binding = binding;
        this.callback = callback;
        this.binding.getRoot().setOnClickListener(v -> callback.handleButtonClick());
    }

    public abstract void onButtonClick(CheckoutViewModel viewModel, CheckoutFragment handler);
}
