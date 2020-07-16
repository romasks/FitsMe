package ru.fitsme.android.presentation.fragments.auth;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import ru.fitsme.android.R;
import ru.fitsme.android.app.ResourceManager;
import ru.fitsme.android.databinding.FragmentCodeBinding;
import ru.fitsme.android.presentation.common.keyboard.KeyboardUtils;
import ru.fitsme.android.presentation.common.listener.BackClickListener;
import ru.fitsme.android.presentation.fragments.base.BaseFragment;

public class CodeFragment extends BaseFragment<CodeViewModel> implements BackClickListener {

    private FragmentCodeBinding binding;

    private CodeTimer timer = null;

    public static CodeFragment newInstance() {
        return new CodeFragment();
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_code;
    }

    @Override
    protected void afterCreateView(View view) {
        binding = FragmentCodeBinding.bind(view);
        binding.setViewModel(viewModel);
        binding.appBar.setBackClickListener(this);
        binding.appBar.setTitle(getString(R.string.auth_code_header_text));
        setUp();
    }

    private void setUp() {
        timer = new CodeTimer(this, binding.timerField, binding.resendCode);

        binding.pinEntryCode.setOnPinEnteredListener(str -> {
            if (str.length() == 4) {
                binding.pinEntryCode.clearFocus();
                viewModel.verifyCode(str.toString());
            }
        });

        binding.pinEntryCode.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) KeyboardUtils.open(requireActivity(), v);
        });

        binding.pinEntryCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                hideCodeError();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        binding.resendCode.setOnClickListener(v -> {
            // Clear and enable entry field
            binding.pinEntryCode.setText("");
            binding.pinEntryCode.setEnabled(true);

            // Disable resend code text
            binding.resendCode.setEnabled(false);
            binding.resendCode.setTextColor(ResourceManager.getColor(R.color.resendCodeInactive));

            // Hide error
            hideCodeError();

            timer.startTimer();
        });

        timer.startTimer();

        binding.pinEntryCode.requestFocus();
    }

    @Override
    protected void setUpObservers() {
        viewModel.isCodeVerified().observe(getViewLifecycleOwner(), this::onCodeVerified);
    }

    private void onCodeVerified(Boolean isCodeVerified) {
        changeCodeErrorVisibility(!isCodeVerified);
    }

    private void hideCodeError() {
        changeCodeErrorVisibility(false);
    }

    private void changeCodeErrorVisibility(boolean showError) {
        ResourceManager.setBackground(
                binding.pinEntryCodeLayout,
                showError ? R.drawable.bg_pin_code_error : R.drawable.bg_pin_code
        );
        binding.errorWrongCode.setVisibility(showError ? View.VISIBLE : View.INVISIBLE);
    }

    @Override
    public void goBack() {
        viewModel.onBackPressed();
    }

    @Override
    public void onDestroy() {
        timer.stopTimer();

        KeyboardUtils.hide(requireActivity(), binding.pinEntryCode);
        super.onDestroy();
    }
}
