package ru.fitsme.android.domain.entities.auth;

public class SignInUpResult {
    private String loginError;
    private String passwordError;
    private String commonError;
    private boolean success;

    private SignInUpResult() {
        success = true;
    }

    public static SignInUpResult build() {
        return new SignInUpResult();
    }

    public String getLoginError() {
        return loginError;
    }

    public void setLoginError(String loginError) {
        this.loginError = loginError;
        success = false;
    }

    public String getPasswordError() {
        return passwordError;
    }

    public void setPasswordError(String passwordError) {
        this.passwordError = passwordError;
        success = false;
    }

    public String getCommonError() {
        return commonError;
    }

    public void setCommonError(String commonError) {
        this.commonError = commonError;
        success = false;
    }

    public boolean isSuccess() {
        return success;
    }
}
