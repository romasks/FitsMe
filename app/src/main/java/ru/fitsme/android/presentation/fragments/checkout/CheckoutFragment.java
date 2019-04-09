package ru.fitsme.android.presentation.fragments.checkout;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.redmadrobot.inputmask.MaskedTextChangedListener;

import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;

import ru.fitsme.android.R;
import ru.fitsme.android.app.App;
import ru.fitsme.android.databinding.FragmentCheckoutBinding;
import ru.fitsme.android.domain.entities.order.Order;
import ru.fitsme.android.domain.interactors.orders.IOrdersInteractor;
import ru.fitsme.android.presentation.fragments.cart.view.CartFragment;

import static ru.fitsme.android.utils.Constants.GONE;
import static ru.fitsme.android.utils.Constants.VISIBLE;

public class CheckoutFragment extends Fragment implements CheckoutBindingEvents {
    @Inject
    IOrdersInteractor ordersInteractor;

    private FragmentCheckoutBinding binding;
    private CheckoutViewModel viewModel;
    private boolean isMaskFilled = false;

    String RU_PHONE_PREFIX = "+7";
    String RU_PHONE_MASK = "+7 ([000]) [000]-[00]-[00]";

    public CheckoutFragment() {
        App.getInstance().getDi().inject(this);
    }

    public static CheckoutFragment newInstance() {
        return new CheckoutFragment();
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_checkout, container, false);
        binding.setBindingEvents(this);
        initPhoneFieldListener();
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

    @Override
    public void goBack() {
        getParentFragment().getChildFragmentManager().beginTransaction()
                .replace(R.id.container, CartFragment.newInstance())
                .commit();
    }

    @Override
    public void clickOrder() {
        String address = String.valueOf(binding.addressCity.getText()) + ", " +
                binding.addressStreet.getText() + ", " +
                binding.addressHouse.getText() + ", " +
                binding.addressFlat.getText();
        viewModel.makeOrder(binding.phoneField.getText().toString(), address);
    }


    private void initPhoneFieldListener() {
        final MaskedTextChangedListener phoneListener = new MaskedTextChangedListener(
                RU_PHONE_MASK, binding.phoneField,
                (maskFilled, extractedValue) -> isMaskFilled = maskFilled
        ) {
            @Override
            public void afterTextChanged(@Nullable Editable edit) {
                super.afterTextChanged(edit);

                if (edit != null && !edit.toString().startsWith(RU_PHONE_PREFIX + " (")) {
                    binding.phoneField.setText(RU_PHONE_PREFIX + " (");
                    binding.phoneField.setSelection(binding.phoneField.getText().length());
                }
            }
        };

        binding.phoneField.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                binding.phoneField.addTextChangedListener(phoneListener);

                if (TextUtils.isEmpty(binding.phoneField.getText()))
                    binding.phoneField.setText(RU_PHONE_PREFIX);

                binding.phoneField.setCursorVisible(false);
                binding.phoneField.post(() -> {
                    binding.phoneField.setSelection(binding.phoneField.getText().length());
                    binding.phoneField.setCursorVisible(true);
                });
            } else {
                if (binding.phoneField == null) return;
                binding.phoneField.removeTextChangedListener(phoneListener);

                if (!isMaskFilled)
                    binding.phoneField.getText().clear();
            }
        });
        binding.phoneField.setText(RU_PHONE_PREFIX);
        binding.phoneField.requestFocus();
    }
}
