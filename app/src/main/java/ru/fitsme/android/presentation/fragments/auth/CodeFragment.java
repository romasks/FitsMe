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

    private FragmentCodeBinding binding;

    private Timer timer = null;
    private int time = 9;

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
            if (str.length() == 4) viewModel.verifyCode();
        });

        binding.resendCode.setOnClickListener(v -> {
            binding.resendCode.setEnabled(false);
            binding.resendCode.setTextColor(getResources().getColor(R.color.resendCodeInactive));
            startTimer();
        });

        startTimer();
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

        timer.cancel();
        timer.purge();
        //timer = null;

        time = 9;
    }

    @Override
    public void onDestroy() {
//        if (timer != null) {
            timer.cancel();
            timer.purge();
//        }
        KeyboardUtils.hide(requireActivity(), binding.pinEntryCode);
        super.onDestroy();
    }
}
