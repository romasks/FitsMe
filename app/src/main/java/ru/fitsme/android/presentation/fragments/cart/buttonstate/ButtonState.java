package ru.fitsme.android.presentation.fragments.cart.buttonstate;

import ru.fitsme.android.databinding.FragmentCartBinding;
import ru.fitsme.android.presentation.fragments.cart.CartFragment;
import ru.fitsme.android.presentation.fragments.cart.CartViewModel;

public abstract class ButtonState {
    FragmentCartBinding binding;
    CartFragment callback;

    ButtonState(FragmentCartBinding binding, CartFragment callback) {
        this.binding = binding;
        this.callback = callback;
        this.binding.getRoot().setOnClickListener(v -> callback.handleButtonClick());
    }

    public abstract void onButtonClick(CartViewModel viewModel, CartFragment handler);
}
