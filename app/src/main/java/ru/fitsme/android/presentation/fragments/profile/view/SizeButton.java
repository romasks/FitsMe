package ru.fitsme.android.presentation.fragments.profile.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TableRow;

import androidx.appcompat.widget.AppCompatButton;

import ru.fitsme.android.R;

public class SizeButton extends AppCompatButton {

    private Boolean isChecked = false;
    private TopSizeDialogFragment.TopSizeObserver topSizeObserver;
    private int id;


    public SizeButton(Context context) {
        super(context);
        setParams();
    }

    public SizeButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        setParams();
    }

    public SizeButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setParams();
    }

    public void setChecked(Boolean checked) {
        isChecked = checked;
        if (isChecked){
            setBackgroundResource(R.drawable.bg_rounded_corners_primary_dark_btn);
            setTextColor(getResources().getColor(R.color.white));
        } else {
            setBackgroundResource(R.drawable.bg_rounded_corners_with_stroke);
            setTextColor(getResources().getColor(R.color.darkGrey));
        }
    }

    public void toggle(){
        isChecked = !isChecked;
        if (topSizeObserver != null){
            topSizeObserver.setState(id, isChecked);
        }
    }

    public void setText(String text) {
        super.setText(text);
    }

    public void setId(int id){
        this.id = id;
    }

    private void setParams(){
        setTextSize(getResources().getDimension(R.dimen.text_size_16sp));
        int padding = (int) getResources().getDimension(R.dimen.padding_8dp);
        int margin = (int) getResources().getDimension(R.dimen.margin_4dp);
        setPadding(padding, 0, padding, 0);
        TableRow.LayoutParams params =
                new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                        TableRow.LayoutParams.WRAP_CONTENT);
        params.setMargins(margin, 0, margin, 0);
        setLayoutParams(params);
        setBackgroundResource(R.drawable.bg_rounded_corners_with_stroke);
        setTextColor(getResources().getColor(R.color.darkGrey));
    }

    public void subscribeOnButton(TopSizeDialogFragment.TopSizeObserver topSizeObserver){
        this.topSizeObserver = topSizeObserver;
        topSizeObserver.addButton(id, this);
    }

    public void unsubscribe(){
        topSizeObserver.resetButton(id);
        this.topSizeObserver = null;
    }
}
