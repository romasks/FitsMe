package ru.fitsme.android.domain.entities.auth;

public class CodeResponse {

    private String status;
    private String verificationCode;

    public CodeResponse(String status, String verificationCode) {
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
