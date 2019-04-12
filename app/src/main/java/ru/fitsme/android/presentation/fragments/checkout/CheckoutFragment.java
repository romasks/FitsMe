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
import android.widget.Toast;

import com.redmadrobot.inputmask.MaskedTextChangedListener;

import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;

import ru.fitsme.android.R;
import ru.fitsme.android.app.App;
import ru.fitsme.android.databinding.FragmentCheckoutBinding;
import ru.fitsme.android.domain.entities.order.Order;
import ru.fitsme.android.domain.entities.order.OrderItem;
import ru.fitsme.android.domain.interactors.orders.IOrdersInteractor;
import ru.fitsme.android.presentation.fragments.cart.view.CartFragment;

import static ru.fitsme.android.utils.Constants.GONE;
import static ru.fitsme.android.utils.Constants.RU_PHONE_MASK;
import static ru.fitsme.android.utils.Constants.RU_PHONE_PREFIX;
import static ru.fitsme.android.utils.Constants.VISIBLE;

public class CheckoutFragment extends Fragment implements CheckoutBindingEvents {
    @Inject
    IOrdersInteractor ordersInteractor;

    private FragmentCheckoutBinding binding;
    private CheckoutViewModel viewModel;
    private boolean isMaskFilled = false;

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
        if (order.getPhoneNumber() != null) binding.phoneNumber.setText(order.getPhoneNumber());
        if (order.getCity() != null) binding.addressCity.setText(order.getCity());
        if (order.getStreet() != null) binding.addressStreet.setText(order.getStreet());
        if (order.getHouseNumber() != null) binding.addressHouse.setText(order.getHouseNumber());
        if (order.getApartment() != null) binding.addressAppartment.setText(order.getApartment());

        int price = 0;
        for (OrderItem item : order.getOrderItemList()) {
            price += item.getPrice();
        }
        binding.price.setText(String.valueOf(price));

        int totalPrice = price + Integer.parseInt(binding.deliveryPrice.getText().toString());
        binding.totalPrice.setText(String.valueOf(totalPrice));
    }

    @Override
    public void goBack() {
        getParentFragment().getChildFragmentManager().beginTransaction()
                .replace(R.id.container, CartFragment.newInstance())
                .commit();
    }

    @Override
    public void onClickMakeOrder() {
        String street = binding.addressStreet.getText().toString();
        String house = binding.addressHouse.getText().toString();
        String apartment = binding.addressAppartment.getText().toString();
        String phone = binding.phoneNumber.getText().toString().replaceAll("[^\\d]", "");
        viewModel.onClickMakeOrder(phone, street, house, apartment);
    }


    private void initPhoneFieldListener() {
        final MaskedTextChangedListener phoneListener = new MaskedTextChangedListener(
                RU_PHONE_MASK, binding.phoneNumber,
                (maskFilled, extractedValue) -> isMaskFilled = maskFilled
        ) {
            @Override
            public void afterTextChanged(@Nullable Editable edit) {
                super.afterTextChanged(edit);

                if (edit != null && !edit.toString().startsWith(RU_PHONE_PREFIX + " (")) {
                    binding.phoneNumber.setText(RU_PHONE_PREFIX + " (");
                    binding.phoneNumber.setSelection(binding.phoneNumber.getText().length());
                }
            }
        };

        binding.phoneNumber.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                binding.phoneNumber.addTextChangedListener(phoneListener);

                if (TextUtils.isEmpty(binding.phoneNumber.getText()))
                    binding.phoneNumber.setText(RU_PHONE_PREFIX);

                binding.phoneNumber.setCursorVisible(false);
                binding.phoneNumber.post(() -> {
                    binding.phoneNumber.setSelection(binding.phoneNumber.getText().length());
                    binding.phoneNumber.setCursorVisible(true);
                });
            } else {
                if (binding.phoneNumber == null) return;
                binding.phoneNumber.removeTextChangedListener(phoneListener);

                if (!isMaskFilled)
                    Toast.makeText(getContext(), R.string.warning_phone_number_is_not_filled, Toast.LENGTH_SHORT).show();
            }
        });
        binding.phoneNumber.setText(RU_PHONE_PREFIX);
        binding.phoneNumber.requestFocus();
    }
}
