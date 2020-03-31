package ru.fitsme.android.presentation.fragments.checkout;

import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.redmadrobot.inputmask.MaskedTextChangedListener;

import ru.fitsme.android.R;
import ru.fitsme.android.app.App;
import ru.fitsme.android.databinding.FragmentCheckoutBinding;
import ru.fitsme.android.presentation.common.listener.BackClickListener;
import ru.fitsme.android.presentation.fragments.base.BaseFragment;
import ru.fitsme.android.presentation.fragments.main.MainFragment;
import timber.log.Timber;

import static ru.fitsme.android.utils.Constants.RU_PHONE_MASK;

public class CheckoutFragment extends BaseFragment<CheckoutViewModel> implements CheckoutBindingEvents, BackClickListener {

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
        binding.appBar.setBackClickListener(this);
        binding.appBar.setTitle(getString(R.string.screen_title_checkout_order));
        setUp();
        setListeners();
    }

    @Override
    protected void setUpObservers() {
        viewModel.getSuccessMakeOrderLiveData().observe(getViewLifecycleOwner(), this::onSuccessMakeOrder);
    }

    @Override
    public void goBack() {
        viewModel.onBackPressed();
        if (getParentFragment() != null) {
            ((MainFragment) getParentFragment()).showBottomNavbar();
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

    private void setUp() {
        if (getParentFragment() != null) {
            ((MainFragment) getParentFragment()).hideBottomNavbar();
        }
    }

    private void setListeners() {
        initPhoneFieldListener(binding.phoneNumber);
        binding.phoneNumber.setText(App.getInstance().getAuthInfo().getLogin());
    }

    private void onSuccessMakeOrder(Boolean successMakeOrder) {
        if (successMakeOrder) goBack();
    }

    private void initPhoneFieldListener(EditText phoneField) {
        MaskedTextChangedListener.Companion.installOn(
                phoneField,
                RU_PHONE_MASK,
                (maskFilled, extractedValue, formattedValue) -> {
                    isMaskFilled = maskFilled;
                    phoneField.setTextColor(getContext().getResources().getColor(R.color.black));
                }
        );
        phoneField.requestFocus();
    }
}
