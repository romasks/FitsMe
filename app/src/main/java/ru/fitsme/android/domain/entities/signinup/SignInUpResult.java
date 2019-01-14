package ru.fitsme.android.domain.entities.signinup;

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

    public SignInUpResult setLoginError(String loginError) {
        this.loginError = loginError;
        success = false;
        return this;
    }

    public String getPasswordError() {
        return passwordError;
    }

    public SignInUpResult setPasswordError(String passwordError) {
        this.passwordError = passwordError;
        success = false;
        return this;
    }

    public String getCommonError() {
        return commonError;
    }

    public SignInUpResult setCommonError(String commonError) {
        this.commonError = commonError;
        success = false;
        return this;
    }

    public boolean isSuccess() {
        return success;
    }
}
