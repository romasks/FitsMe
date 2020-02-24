package ru.fitsme.android.presentation.fragments.profile.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.TableRow;

import androidx.appcompat.widget.AppCompatButton;

import ru.fitsme.android.R;

public class SizeButton extends AppCompatButton {

    private Boolean isChecked = false;
    private OnSizeButtonClickCallback sizeCallback;
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
        if (sizeCallback != null){
            sizeCallback.setState(id, isChecked);
        }
    }

    public void setText(String text) {
        super.setText(text);
    }

    public void setId(int id){
        this.id = id;
    }

    private void setParams(){
        setTextSize(TypedValue.COMPLEX_UNIT_SP, getResources().getDimension(R.dimen.text_size_10sp));
        int padding = (int) getResources().getDimension(R.dimen.padding_8dp);
        int margin = (int) getResources().getDimension(R.dimen.margin_4dp);
        setPadding(padding, 0, padding, 0);
        TableRow.LayoutParams params =
                new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                        TableRow.LayoutParams.WRAP_CONTENT);
        params.setMargins(margin, 0, margin, 0);
        setTextAlignment(TEXT_ALIGNMENT_CENTER);
        setLayoutParams(params);
        setBackgroundResource(R.drawable.bg_rounded_corners_with_stroke);
        setTextColor(getResources().getColor(R.color.darkGrey));
    }

    public void setCallback(OnSizeButtonClickCallback sizeCallBack){
        this.sizeCallback = sizeCallBack;
        sizeCallBack.addButton(id, this);
    }

    interface OnSizeButtonClickCallback{
        void setState(int id, Boolean isChecked);
        void addButton(int id, SizeButton button);
    }
}
