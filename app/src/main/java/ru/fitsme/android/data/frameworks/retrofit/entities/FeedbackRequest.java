package ru.fitsme.android.data.frameworks.retrofit.entities;

public class FeedbackRequest {
    private String feedback;

    public FeedbackRequest(String feedback) {
        this.feedback = feedback;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }
}
