package ru.fitsme.android.presentation.fragments.auth;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import java.text.DecimalFormat;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import ru.fitsme.android.R;
import ru.fitsme.android.app.ResourceManager;

class CodeTimer {

    private static final int REPEAT_TIME = 60;

    private Timer timer = null;
    private int time = REPEAT_TIME;

    private Activity activity;
    private TextView timerField;
    private TextView resendCodeButton;

    CodeTimer(Fragment fragment, TextView timerField, TextView resendCodeButton) {
        activity = fragment.requireActivity();
        this.timerField = timerField;
        this.resendCodeButton = resendCodeButton;
    }

    void startTimer() {
        timerField.setVisibility(View.VISIBLE);
        timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                activity.runOnUiThread(() -> {
                    DecimalFormat formatter = new DecimalFormat("00");
                    timerField.setText(String.format(Locale.getDefault(), "Через 0:%s", formatter.format(time)));

                    if (time > 0) time -= 1;
                    else stopTimerPrivate();
                });
            }
        };
        timer.scheduleAtFixedRate(timerTask, 0, 1000);
    }

    private void stopTimerPrivate() {
        resendCodeButton.setEnabled(true);
        resendCodeButton.setTextColor(ResourceManager.getColor(R.color.resendCodeActive));
        timerField.setVisibility(View.INVISIBLE);

        stopTimer();

        time = REPEAT_TIME;
    }

    void stopTimer() {
        if (timer != null) {
            timer.cancel();
            timer.purge();
            timer = null;
        }
    }
}
