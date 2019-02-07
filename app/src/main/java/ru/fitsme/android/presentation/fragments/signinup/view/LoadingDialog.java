package ru.fitsme.android.presentation.fragments.signinup.view;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;

import ru.fitsme.android.R;

public class LoadingDialog {

    private AlertDialog alertDialog;

    public void show(Context context) {
        if (alertDialog == null) {
            alertDialog = new AlertDialog.Builder(context)
                    .setCancelable(false)
                    .setView(LayoutInflater.from(context).inflate(R.layout.loading_dialog, null))
                    .create();
            alertDialog.show();
        }
    }

    public void hide() {
        if (alertDialog != null) {
            alertDialog.cancel();
            alertDialog = null;
        }
    }
}
