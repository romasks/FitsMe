package ru.fitsme.android.presentation.fragments.auth;

import android.view.View;

import java.text.DecimalFormat;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import ru.fitsme.android.R;
import ru.fitsme.android.databinding.FragmentCodeBinding;
import ru.fitsme.android.presentation.common.keyboard.KeyboardUtils;
import ru.fitsme.android.presentation.common.listener.BackClickListener;
import ru.fitsme.android.presentation.fragments.base.BaseFragment;

public class CodeFragment extends BaseFragment<CodeViewModel> implements CodeBindingEvents, BackClickListener {

    private static final int REPEAT_TIME = 60;

    private FragmentCodeBinding binding;

    private Timer timer = null;
    private int time = REPEAT_TIME;

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
        binding.setBindingEvents(this);
        binding.setViewModel(viewModel);
        binding.appBar.setBackClickListener(this);
        binding.appBar.setTitle(getString(R.string.auth_code_header_text));
        setUp();
    }

    private void setUp() {
//        binding.pinEntryCode.requestFocus();
        // TODO: do smth with show keyboard
        KeyboardUtils.open(requireActivity(), binding.pinEntryCode);

        binding.pinEntryCode.setOnPinEnteredListener(str -> {
            if (str.length() == 4) viewModel.verifyCode(str.toString());
        });

        binding.resendCode.setOnClickListener(v -> {
            // Clear and enable entry field
            binding.pinEntryCode.setText("");
            binding.pinEntryCode.setEnabled(true);

            // Disable resend code text
            binding.resendCode.setEnabled(false);
            binding.resendCode.setTextColor(getResources().getColor(R.color.resendCodeInactive));

            // Hide error
            binding.pinEntryCodeLayout.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_pin_code));
            binding.errorWrongCode.setVisibility(View.INVISIBLE);

            startTimer();
        });

        startTimer();
    }

    @Override
    protected void setUpObservers() {
        viewModel.isCodeVerified().observe(getViewLifecycleOwner(), this::onCodeVerified);
    }

    private void onCodeVerified(Boolean isCodeVerified) {
        if (isCodeVerified) {
            binding.pinEntryCodeLayout.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_pin_code));
            binding.errorWrongCode.setVisibility(View.INVISIBLE);
        } else {
            binding.pinEntryCodeLayout.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_pin_code_error));
            binding.errorWrongCode.setVisibility(View.VISIBLE);
            binding.pinEntryCode.setEnabled(false);
        }
    }

    @Override
    public void goBack() {
        viewModel.onBackPressed();
    }

    private void startTimer() {
        binding.timerField.setVisibility(View.VISIBLE);
        timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                requireActivity().runOnUiThread(() -> {
                    DecimalFormat formatter = new DecimalFormat("00");
                    binding.timerField.setText(String.format(Locale.getDefault(), "Через 0:%s", formatter.format(time)));
                    if (time > 0) time -= 1;
                    else stopTimer();
                });
            }
        };
        timer.scheduleAtFixedRate(timerTask, 0, 1000);
    }

    private void stopTimer() {
        binding.resendCode.setEnabled(true);
        binding.resendCode.setTextColor(getResources().getColor(R.color.resendCodeActive));
        binding.timerField.setVisibility(View.INVISIBLE);

        if (timer != null) {
            timer.cancel();
            timer.purge();
            timer = null;
        }

        time = REPEAT_TIME;
    }

    @Override
    public void onDestroy() {
        if (timer != null) {
            timer.cancel();
            timer.purge();
            timer = null;
        }

        KeyboardUtils.hide(requireActivity(), binding.pinEntryCode);
        super.onDestroy();
    }
}
