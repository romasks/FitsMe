package ru.fitsme.android.presentation.common.binding;

import android.databinding.BindingAdapter;
import android.graphics.drawable.Drawable;
import android.support.design.widget.TextInputLayout;
import android.widget.ImageView;

import ru.fitsme.android.app.GlideApp;

public class BindingAdapterUtils {

    // TextInputLayout.setError
    @BindingAdapter("errorText")
    public static void setErrorMessage(TextInputLayout view, String errorMessage) {
        view.setError(errorMessage);
    }

    // FavouriteItem ImageView setImage
    @BindingAdapter({"app:imageUrl", "app:defaultImage"})
    public static void loadImage(ImageView imageView, String imageUrl, Drawable defaultImage) {
        GlideApp.with(imageView.getContext())
                .load(imageUrl)
                .placeholder(defaultImage)
                .error(defaultImage)
                .into(imageView);
    }
}
