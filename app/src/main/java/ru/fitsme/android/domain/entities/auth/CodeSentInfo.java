package ru.fitsme.android.domain.entities.auth;

public class CodeSentInfo {

    private String status;
    private String verificationCode;

    public CodeSentInfo(String status, String verificationCode) {
        this.status = status;
        this.verificationCode = verificationCode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getVerificationCode() {
        return verificationCode;
    }

    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
    }
}
