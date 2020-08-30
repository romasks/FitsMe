package ru.fitsme.android.presentation.fragments.auth;

import android.graphics.Color;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.EditText;

import com.redmadrobot.inputmask.MaskedTextChangedListener;

import org.jetbrains.annotations.NotNull;

import ru.fitsme.android.R;
import ru.fitsme.android.databinding.FragmentAuthByPhoneNumBinding;
import ru.fitsme.android.presentation.fragments.base.BaseFragment;

import static ru.fitsme.android.utils.Constants.PHONE_CODE_MASK;
import static ru.fitsme.android.utils.Constants.PHONE_MASK_WITHOUT_CODE;
import static ru.fitsme.android.utils.Constants.RU_PHONE_PREFIX;

public class NumberFragment extends BaseFragment<NumberViewModel> implements NumberBindingEvents {

    private FragmentAuthByPhoneNumBinding binding;

    public static NumberFragment newInstance() {
        return new NumberFragment();
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_auth_by_phone_num;
    }

    @Override
    protected void afterCreateView(View view) {
        binding = FragmentAuthByPhoneNumBinding.bind(view);
        binding.setBindingEvents(this);
        binding.setViewModel(viewModel);
        setCountyFlag();
        setListeners();
        setUp();
    }

    @Override
    public void onGetCodeClicked() {
        String countryCode = binding.fragmentPhoneAuthCodeEt.getText().toString();
        String phoneNumber = binding.fragmentPhoneAuthNumberEt.getText().toString();
        String cleanNumber = getCleanNumber(phoneNumber);
        if (cleanNumber.length() != 10) {
            binding.fragmentPhoneAuthWrongNumTv.setVisibility(View.VISIBLE);
            binding.fragmentPhoneAuthWrongNumTv.setText(getString(R.string.wrong_phone_number));
            binding.fragmentPhoneAuthNumberEt.setTextColor(Color.RED);
        } else {
            if (!countryCode.equals(RU_PHONE_PREFIX)) {
                binding.fragmentPhoneAuthCodeEt.setTextColor(Color.RED);
            } else {
                viewModel.sendPhoneNumber(countryCode + cleanNumber);
            }
        }
    }

    @Override
    public void onAgreementClicked() {
        viewModel.goToAgreement();
    }

    private void setListeners() {
        initPhoneFieldListener(binding.fragmentPhoneAuthNumberEt);
        initCodeFieldListener(binding.fragmentPhoneAuthCodeEt);
    }

    private void initCodeFieldListener(EditText codeET) {
        MaskedTextChangedListener.Companion.installOn(
                codeET,
                PHONE_CODE_MASK,
                (maskFilled, extractedValue, formattedValue) -> {
                    codeET.setTextColor(NumberFragment.this.getContext().getResources().getColor(R.color.black));
                    setCountyFlag(formattedValue);
                }
        );
    }

    private void setCountyFlag() {
        setCountyFlag(binding.fragmentPhoneAuthCodeEt.getText().toString());
    }

    private void setCountyFlag(String string) {
        if (string.equals(RU_PHONE_PREFIX)) {
            binding.fragmentPhoneAuthCodeEt
                    .setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_rus_flag, 0, 0, 0);
        } else {
            binding.fragmentPhoneAuthCodeEt
                    .setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
        }
    }

    private void initPhoneFieldListener(EditText phoneField) {
        MaskedTextChangedListener.Companion.installOn(
                phoneField,
                PHONE_MASK_WITHOUT_CODE,
                (maskFilled, extractedValue, formattedValue) -> {
                    phoneField.setTextColor(getContext().getResources().getColor(R.color.black));
                    binding.fragmentPhoneAuthGetCodeBtn.setEnabled(extractedValue.length() == 10);
                }
        );
        phoneField.requestFocus();
    }

    @NotNull
    private String getCleanNumber(String string) {
        return string.replaceAll("[()-[\\s]]", "");
    }

    private void setUp() {
        String simpleText = getContext().getString(R.string.cart_user_agreement_1);
        String spannedText = getContext().getString(R.string.cart_user_agreement_2);

        SpannableString ss = new SpannableString(simpleText + " " + spannedText);
        ss.setSpan(
                new ForegroundColorSpan(getContext().getResources().getColor(R.color.colorPrimaryDark)),
                simpleText.length(),
                (simpleText + spannedText).length(),
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        );

        binding.tvAgreement.setText(ss);
    }
}
