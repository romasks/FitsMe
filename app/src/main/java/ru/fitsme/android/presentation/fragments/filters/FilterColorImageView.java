package ru.fitsme.android.presentation.fragments.filters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.util.AttributeSet;
import android.widget.Checkable;

import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.graphics.drawable.DrawableCompat;

import ru.fitsme.android.R;
import ru.fitsme.android.domain.entities.clothes.FilterColor;

public class FilterColorImageView extends AppCompatImageView implements Checkable {
    private FilterColor filterColor;

    public FilterColorImageView(Context context) {
        super(context);
    }

    public FilterColorImageView(Context context, FilterColor filterColor){
        super(context);
        this.filterColor = filterColor;
        setChecked(filterColor.isChecked());
        this.isClickable();
        this.isFocusable();
    }

    public FilterColorImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FilterColorImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void setChecked(boolean isChecked) {
        filterColor.setChecked(isChecked);
        if (isChecked){
            Drawable checkedUnwrappedDrawable = AppCompatResources.getDrawable(getContext(), R.drawable.ic_check_circle_checked);
            Drawable checkedDrawable = checkedUnwrappedDrawable.getConstantState().newDrawable().mutate();
            Drawable checkedWrappedDrawable = DrawableCompat.wrap(checkedDrawable);
            DrawableCompat.setTint(checkedWrappedDrawable, Color.parseColor(filterColor.getColorHex()));
            if (filterColor.getColorHex().equals("#ffffff")){
                Drawable[] layers = new Drawable[2];
                layers[0] = (getContext().getResources().getDrawable(R.drawable.ic_check_circle_background));
                layers[1] = checkedWrappedDrawable;
                LayerDrawable layerDrawable = new LayerDrawable(layers);
                setImageDrawable(layerDrawable);
            } else {
                setImageDrawable(checkedWrappedDrawable);
            }
            
        } else {
            Drawable uncheckedUnwrappedDrawable = AppCompatResources.getDrawable(getContext(), R.drawable.ic_check_circle_unchecked);
            Drawable uncheckedDrawable = uncheckedUnwrappedDrawable.getConstantState().newDrawable().mutate();
            Drawable uncheckedWrappedDrawable = DrawableCompat.wrap(uncheckedDrawable);
            if (filterColor.getColorHex() != null) {
                DrawableCompat.setTint(uncheckedWrappedDrawable, Color.parseColor(filterColor.getColorHex()));
                if (filterColor.getColorHex().equals("#ffffff")) {
                    Drawable[] layers = new Drawable[2];
                    layers[0] = (getResources().getDrawable(R.drawable.ic_check_circle_background));
                    layers[1] = uncheckedWrappedDrawable;
                    LayerDrawable layerDrawable = new LayerDrawable(layers);
                    setImageDrawable(layerDrawable);
                } else {
                    setImageDrawable(uncheckedWrappedDrawable);
                }
            }
        }
    }

    @Override
    public boolean isChecked() {
        return filterColor.isChecked();
    }

    @Override
    public void toggle() {
        setChecked(!filterColor.isChecked());
    }
}
