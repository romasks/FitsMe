package ru.fitsme.android.presentation.fragments.checkout;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.redmadrobot.inputmask.MaskedTextChangedListener;

import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;

import ru.fitsme.android.R;
import ru.fitsme.android.data.models.OrderModel;
import ru.fitsme.android.databinding.FragmentCheckoutBinding;
import ru.fitsme.android.domain.entities.order.Order;
import ru.fitsme.android.domain.interactors.orders.IOrdersInteractor;
import ru.fitsme.android.presentation.fragments.base.BaseFragment;
import ru.fitsme.android.presentation.fragments.base.ViewModelFactory;
import ru.fitsme.android.presentation.fragments.cart.CartFragment;
import timber.log.Timber;

import static ru.fitsme.android.utils.Constants.GONE;
import static ru.fitsme.android.utils.Constants.RU_PHONE_MASK;
import static ru.fitsme.android.utils.Constants.RU_PHONE_PREFIX;
import static ru.fitsme.android.utils.Constants.VISIBLE;

public class CheckoutFragment extends BaseFragment<CheckoutViewModel> implements CheckoutBindingEvents {

    @Inject
    IOrdersInteractor ordersInteractor;

    private FragmentCheckoutBinding binding;
    private boolean isMaskFilled = false;

    public static CheckoutFragment newInstance() {
        return new CheckoutFragment();
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_checkout, container, false);
        binding.setBindingEvents(this);
        initPhoneFieldListener(binding.phoneNumber);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = ViewModelProviders.of(this,
                new ViewModelFactory(ordersInteractor)).get(CheckoutViewModel.class);
        if (savedInstanceState == null) {
            viewModel.init();
        }
        binding.setViewModel(viewModel);

        viewModel.loading.set(VISIBLE);
        viewModel.getOrderLiveData().observe(this, this::onLoadOrder);
        viewModel.getSuccessMakeOrderLiveData().observe(this, this::onSuccessMakeOrder);
    }

    private void onLoadOrder(Order order) {
        viewModel.loading.set(GONE);
        viewModel.orderModel.set(new OrderModel(order));
    }

    private void onSuccessMakeOrder(Boolean successMakeOrder) {
        if (successMakeOrder) goBack();
    }

    @Override
    public void goBack() {
        getParentFragment().getChildFragmentManager().beginTransaction()
                .replace(R.id.container, CartFragment.newInstance())
                .commit();
    }

    @Override
    public void onClickMakeOrder() {
        Timber.tag(getClass().getName()).d("isMaskFilled: %s", isMaskFilled);
        if (!isMaskFilled) return;
        viewModel.onClickMakeOrder();
    }


    void initPhoneFieldListener(EditText phoneField) {
        final MaskedTextChangedListener phoneListener = new MaskedTextChangedListener(
                RU_PHONE_MASK, phoneField,
                (maskFilled, extractedValue) -> isMaskFilled = maskFilled
        ) {
            @Override
            public void afterTextChanged(@Nullable Editable edit) {
                super.afterTextChanged(edit);

                if (edit != null && !edit.toString().startsWith(RU_PHONE_PREFIX + " (")) {
                    phoneField.setText(RU_PHONE_PREFIX + " (");
                    phoneField.setSelection(phoneField.getText().length());
                }
            }
        };

        phoneField.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                phoneField.addTextChangedListener(phoneListener);

                if (TextUtils.isEmpty(phoneField.getText()))
                    phoneField.setText(RU_PHONE_PREFIX);

                phoneField.setCursorVisible(false);
                phoneField.post(() -> {
                    phoneField.setSelection(phoneField.getText().length());
                    phoneField.setCursorVisible(true);
                });
            } else {
                if (phoneField == null) return;
                phoneField.removeTextChangedListener(phoneListener);

                if (!isMaskFilled)
                    Toast.makeText(getContext(), R.string.warning_phone_number_is_not_filled, Toast.LENGTH_SHORT).show();
            }
        });
        phoneField.setText(RU_PHONE_PREFIX);
        phoneField.requestFocus();
    }
}
