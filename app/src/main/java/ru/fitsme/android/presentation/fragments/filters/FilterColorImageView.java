package ru.fitsme.android.presentation.fragments.filters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.Checkable;

import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.graphics.drawable.DrawableCompat;

import ru.fitsme.android.R;
import ru.fitsme.android.domain.entities.clothes.FilterColor;

public class FilterColorImageView extends AppCompatImageView implements Checkable {
    private FilterColor filterColor;
    private Context context;

    public FilterColorImageView(Context context) {
        super(context);
        this.context = context;
    }

    public FilterColorImageView(Context context, FilterColor filterColor){
        super(context);
        this.context = context;
        this.filterColor = filterColor;
        setChecked(filterColor.isChecked());
        this.isClickable();
        this.isFocusable();
    }

    public FilterColorImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    public FilterColorImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
    }

    @Override
    public void setChecked(boolean isChecked) {
        filterColor.setChecked(isChecked);
        if (isChecked){
            Drawable checkedUnwrappedDrawable = AppCompatResources.getDrawable(context, R.drawable.ic_check_circle_checked);
            Drawable checkedDrawable = checkedUnwrappedDrawable.getConstantState().newDrawable().mutate();
            Drawable checkedWrappedDrawable = DrawableCompat.wrap(checkedDrawable);
            DrawableCompat.setTint(checkedWrappedDrawable, Color.parseColor(filterColor.getColorHex()));
            setImageDrawable(checkedWrappedDrawable);
        } else {
            Drawable uncheckedUnwrappedDrawable = AppCompatResources.getDrawable(context, R.drawable.ic_check_circle_unchecked);
            Drawable uncheckedDrawable = uncheckedUnwrappedDrawable.getConstantState().newDrawable().mutate();
            Drawable uncheckedWrappedDrawable = DrawableCompat.wrap(uncheckedDrawable);
            DrawableCompat.setTint(uncheckedWrappedDrawable, Color.parseColor(filterColor.getColorHex()));
            setImageDrawable(uncheckedWrappedDrawable);
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
