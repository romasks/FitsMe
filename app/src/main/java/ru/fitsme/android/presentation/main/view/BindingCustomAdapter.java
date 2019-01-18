package ru.fitsme.android.presentation.main.view;

import android.databinding.BindingAdapter;
import android.support.design.widget.TextInputLayout;

public class BindingCustomAdapter {

    //TextInputLayout.setError
    @BindingAdapter("errorText")
    public static void setErrorMessage(TextInputLayout view, String errorMessage) {
        view.setError(errorMessage);
    }
}
