package ru.fitsme.android.presentation.fragments.checkout;

import android.annotation.SuppressLint;
import android.text.Editable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.redmadrobot.inputmask.MaskedTextChangedListener;

import ru.fitsme.android.R;
import ru.fitsme.android.app.App;
import ru.fitsme.android.databinding.FragmentCheckoutBinding;
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
        binding.phoneNumber.setText(App.getInstance().getAuthInfo().getLogin());
        initPhoneFieldListener(binding.phoneNumber);
    }

    @Override
    protected void setUpObservers() {
        viewModel.getSuccessMakeOrderLiveData().observe(getViewLifecycleOwner(), this::onSuccessMakeOrder);
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
        if (binding.addressCity.getText().toString().isEmpty() ||
                binding.addressStreet.getText().toString().isEmpty() ||
                binding.addressHouse.getText().toString().isEmpty() ||
                binding.addressApartment.getText().toString().isEmpty()) {
            Toast.makeText(getContext(), R.string.checkout_warning_some_fields_is_empty, Toast.LENGTH_SHORT).show();
            return;
        }
        viewModel.onClickMakeOrder();
    }


    private void initPhoneFieldListener(EditText phoneField) {
        final MaskedTextChangedListener phoneListener = new MaskedTextChangedListener(
                RU_PHONE_MASK, phoneField,
                (maskFilled, extractedValue, formattedValue) -> isMaskFilled = maskFilled
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
}
