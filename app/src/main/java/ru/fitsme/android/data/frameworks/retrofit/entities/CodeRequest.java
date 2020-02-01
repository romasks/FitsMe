package ru.fitsme.android.data.frameworks.retrofit.entities;

public class CodeRequest {
    private String phoneNumber;

    public CodeRequest(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
