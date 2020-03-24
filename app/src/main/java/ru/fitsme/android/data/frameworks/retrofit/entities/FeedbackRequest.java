package ru.fitsme.android.data.frameworks.retrofit.entities;

import com.google.gson.annotations.SerializedName;

public class FeedbackRequest {

    @SerializedName("username")
    private String username;

    @SerializedName("email")
    private String email;

    @SerializedName("text")
    private String text;

    public FeedbackRequest(String username, String email, String text) {
        this.username = username;
        this.email = email;
        this.text = text;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getText() {
        return text;
    }
}
