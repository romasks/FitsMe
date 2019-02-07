package ru.fitsme.android.presentation.common.binding;

import android.databinding.BindingAdapter;
import android.support.design.widget.TextInputLayout;

public class BindingAdapterUtils {

    //TextInputLayout.setError
    @BindingAdapter("errorText")
    public static void setErrorMessage(TextInputLayout view, String errorMessage) {
        view.setError(errorMessage);
    }
}
