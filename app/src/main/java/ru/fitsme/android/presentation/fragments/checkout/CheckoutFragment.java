package ru.fitsme.android.presentation.fragments.checkout;

import android.annotation.SuppressLint;
import android.text.Editable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.redmadrobot.inputmask.MaskedTextChangedListener;

import javax.inject.Inject;

import androidx.annotation.Nullable;
import ru.fitsme.android.R;
import ru.fitsme.android.data.models.OrderModel;
import ru.fitsme.android.databinding.FragmentCheckoutBinding;
import ru.fitsme.android.domain.entities.order.Order;
import ru.fitsme.android.domain.interactors.orders.IOrdersInteractor;
import ru.fitsme.android.presentation.fragments.base.BaseFragment;
import ru.fitsme.android.presentation.fragments.cart.CartFragment;
import timber.log.Timber;

import static ru.fitsme.android.utils.Constants.RU_PHONE_MASK;
import static ru.fitsme.android.utils.Constants.RU_PHONE_PREFIX;

public class CheckoutFragment extends BaseFragment<CheckoutViewModel> implements CheckoutBindingEvents {

    private FragmentCheckoutBinding binding;
    private boolean isMaskFilled = false;

    public static CheckoutFragment newInstance() {
        return new CheckoutFragment();
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_checkout;
    }

    @Override
    protected void afterCreateView(View view) {
        binding = FragmentCheckoutBinding.bind(view);
        binding.setBindingEvents(this);
        binding.setViewModel(viewModel);
        initPhoneFieldListener(binding.phoneNumber);
    }

    @Override
    protected void setUpRecyclers() {

    }

    @Override
    protected void setUpObservers() {
        viewModel.getOrderLiveData().observe(this, this::onLoadOrder);
        viewModel.getSuccessMakeOrderLiveData().observe(this, this::onSuccessMakeOrder);
    }

    private void onLoadOrder(Order order) {
        viewModel.orderModel.set(new OrderModel(order));
    }

    private void onSuccessMakeOrder(Boolean successMakeOrder) {
        if (successMakeOrder) goBack();
    }

    @Override
    public void goBack() {
        if (getParentFragment() != null) {
            getParentFragment().getChildFragmentManager().beginTransaction()
                    .replace(R.id.fragment_main_container, CartFragment.newInstance())
                    .commit();
        }
    }

    @Override
    public void onClickMakeOrder() {
        Timber.tag(getClass().getName()).d("isMaskFilled: %s", isMaskFilled);
        if (!isMaskFilled) {
            Toast.makeText(getContext(), R.string.warning_phone_number_is_not_filled, Toast.LENGTH_SHORT).show();
            return;
        }
        viewModel.onClickMakeOrder();
    }


    private void initPhoneFieldListener(EditText phoneField) {
        final MaskedTextChangedListener phoneListener = new MaskedTextChangedListener(
                RU_PHONE_MASK, phoneField,
                (maskFilled, extractedValue) -> isMaskFilled = maskFilled
        ) {
            @SuppressLint("SetTextI18n")
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
                phoneField.removeTextChangedListener(phoneListener);

                if (!isMaskFilled)
                    Toast.makeText(getContext(), R.string.warning_phone_number_is_not_filled, Toast.LENGTH_SHORT).show();
            }
        });
        phoneField.setText(RU_PHONE_PREFIX);
        phoneField.requestFocus();
    }

    @Override
    public void onBackPressed() {

    }
}
