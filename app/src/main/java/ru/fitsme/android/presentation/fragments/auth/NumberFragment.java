package ru.fitsme.android.presentation.fragments.auth;

import android.graphics.Color;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import org.jetbrains.annotations.NotNull;

import ru.fitsme.android.R;
import ru.fitsme.android.databinding.FragmentAuthByPhoneNumBinding;
import ru.fitsme.android.presentation.fragments.base.BaseFragment;

public class NumberFragment extends BaseFragment<NumberViewModel> implements NumberBindingEvents {

    private FragmentAuthByPhoneNumBinding binding;
    private MyTextWatcher textWatcher = new MyTextWatcher();

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
    }

    private void setListeners() {
        binding.fragmentPhoneAuthNumberEt.addTextChangedListener(textWatcher);
        binding.fragmentPhoneAuthCodeEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                binding.fragmentPhoneAuthCodeEt.setTextColor(Color.BLACK);
            }
        });
    }

    private void setCountyFlag() {
        if (binding.fragmentPhoneAuthCodeEt.getText().toString().equals("+7")){
            binding.fragmentPhoneAuthCodeEt
                    .setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_rus_flag, 0, 0, 0);
        }
    }

    @Override
    public void onBackPressed() {
        viewModel.onBackPressed();
    }

    @Override
    public void onGetCodeClicked() {
        String countryCode = binding.fragmentPhoneAuthCodeEt.getText().toString();
        String phoneNumber = binding.fragmentPhoneAuthNumberEt.getText().toString();
        String cleanNumber = getCleanNumber(phoneNumber);
        if (cleanNumber.length() != 10){
            binding.fragmentPhoneAuthWrongNumTv.setVisibility(View.VISIBLE);
            binding.fragmentPhoneAuthNumberEt.setTextColor(Color.RED);
        } else {
            if (!countryCode.equals("+7")){
                binding.fragmentPhoneAuthCodeEt.setTextColor(Color.RED);
            } else {
                viewModel.getAuthCode(countryCode + cleanNumber);
            }
        }
    }


    private class MyTextWatcher implements TextWatcher{
        private boolean wasIncreased;

        @Override
        public void beforeTextChanged(CharSequence s, int start,
                                      int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            wasIncreased = count - before > 0;
        }

        @Override
        public void afterTextChanged(Editable editable) {
            binding.fragmentPhoneAuthNumberEt.setTextColor(Color.BLACK);
            binding.fragmentPhoneAuthNumberEt.removeTextChangedListener(textWatcher);
            int position = binding.fragmentPhoneAuthNumberEt.getSelectionStart();
            String string = editable.toString();
            String clean = getCleanNumber(string);
            StringBuilder builder = new StringBuilder(clean);
            if (builder.length() > 0){
                builder.insert(0, '(');
            }
            if (builder.length() > 4){
                builder.insert(4, ")");
            }
            if (builder.length() > 8){
                builder.insert(8, "-");
            }
            if (builder.length() > 11){
                builder.insert(11 , "-");
            }
            binding.fragmentPhoneAuthNumberEt.setText(builder);
            if (wasIncreased){
                if (position == 1 || position == 5 || position == 9 || position == 12){
                    position += 1;
                }
            } else {
                if (position == 1 || position == 5 || position == 9 || position == 12){
                    position -= 1;
                }
                if (position == 0 && builder.length() != 0){
                    position = 1;
                }
            }
            binding.fragmentPhoneAuthNumberEt.setSelection(position);
            binding.fragmentPhoneAuthNumberEt.addTextChangedListener(textWatcher);
        }
    }

    @NotNull
    private String getCleanNumber(String string) {
        return string.replaceAll("[()-]", "");
    }
}
