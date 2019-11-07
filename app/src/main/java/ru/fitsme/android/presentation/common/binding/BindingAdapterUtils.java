package ru.fitsme.android.presentation.common.binding;

import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.google.android.material.textfield.TextInputLayout;

import ru.fitsme.android.app.GlideApp;

public class BindingAdapterUtils {

    // TextInputLayout.setError
    @BindingAdapter("errorText")
    public static void setErrorMessage(TextInputLayout view, String errorMessage) {
        view.setError(errorMessage);
    }

    // FavouriteItem ImageView setImage
    @BindingAdapter({"imageUrl", "defaultImage"})
    public static void loadImage(ImageView imageView, String imageUrl, Drawable defaultImage) {
        GlideApp.with(imageView.getContext())
                .load(imageUrl)
                .placeholder(defaultImage)
                .error(defaultImage)
                .into(imageView);
    }
}
