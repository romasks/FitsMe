package ru.fitsme.android.presentation.fragments.feedback;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.text.InputType;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import ru.fitsme.android.R;
import ru.fitsme.android.presentation.common.keyboard.KeyboardUtils;

public class FeedbackFieldView extends FrameLayout {

    private TextView label;
    private EditText field;
    private boolean isKeyboardVisible = false;

    @StringRes
    private Integer hint;

    public FeedbackFieldView(@NonNull Context context) {
        super(context);
        init(context, null);
    }

    public FeedbackFieldView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public FeedbackFieldView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    public boolean isEmpty() {
        return field.getText().toString().isEmpty();
    }

    public String getText() {
        return field.getText().toString();
    }

    public void hideError() {
        field.setHint(hint);
        findViewById(R.id.error_icon).setVisibility(View.GONE);
        findViewById(R.id.error_line).setVisibility(View.GONE);
    }

    public void showErrorWithHint() {
        field.setHint(R.string.feedback_error_text);
        showError();
    }

    public void showError() {
        findViewById(R.id.error_icon).setVisibility(View.VISIBLE);
        findViewById(R.id.error_line).setVisibility(View.VISIBLE);
    }

    private void init(Context context, AttributeSet attrs) {
        inflate(context, R.layout.view_feedback_field, this);
        label = findViewById(R.id.label);
        field = findViewById(R.id.field);
        setAttrs(context, attrs);

        field.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) hideError();

            if (isKeyboardVisible) scrollUp();
            else this.postDelayed(this::scrollUp, 200);
        });

        KeyboardUtils.addKeyboardToggleListener((Activity) context, this::setKeyboardVisibility);
    }

    private void scrollUp() {
        ViewUtils.scrollUp(this);
    }

    private void setKeyboardVisibility(boolean isVisible) {
        isKeyboardVisible = isVisible;
    }

    private void setAttrs(Context context, AttributeSet attrs) {
        TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.FeedbackFieldView);
        label.setText(attributes.getResourceId(R.styleable.FeedbackFieldView_fieldLabel, 0));
        hint = attributes.getResourceId(R.styleable.FeedbackFieldView_fieldHint, 0);
        field.setHint(hint);
        int inputType = InputType.TYPE_CLASS_TEXT;
        switch (attributes.getString(R.styleable.FeedbackFieldView_inputType)) {
            case "textPersonName": {
                inputType |= InputType.TYPE_TEXT_VARIATION_PERSON_NAME;
                break;
            }
            case "textEmailAddress": {
                inputType |= InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS;
                break;
            }
            /*case "textMultiLine": {
                inputType |= InputType.TYPE_TEXT_FLAG_MULTI_LINE;
                break;
            }*/
            default:
                break;
        }
        field.setInputType(inputType);
        attributes.recycle();
    }
}
