package ru.fitsme.android.domain.entities.feedback;

import androidx.annotation.NonNull;

public enum FeedbackMessage {
    SUCCESSFULLY_SENT("succesfully_sent"),
    RECIPIENT_NOT_SET("recipient_not_set"),
    SOMETHING_WENT_WRONG("not_send");

    private String msg;

    FeedbackMessage(String msg) {
        this.msg = msg;
    }


    @NonNull
    @Override
    public String toString() {
        return msg;
    }
}
