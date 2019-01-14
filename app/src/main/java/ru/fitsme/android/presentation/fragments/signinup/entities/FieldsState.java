package ru.fitsme.android.presentation.fragments.signinup.entities;

public class FieldsState {
    private String loginError;
    private String passwordError;
    private String commonError;
    private boolean loading;

    public String getLoginError() {
        return loginError;
    }

    void setLoginError(String loginError) {
        this.loginError = loginError;
    }

    public String getPasswordError() {
        return passwordError;
    }

    void setPasswordError(String passwordError) {
        this.passwordError = passwordError;
    }

    public String getCommonError() {
        return commonError;
    }

    void setCommonError(String commonError) {
        this.commonError = commonError;
    }

    public boolean isLoading() {
        return loading;
    }

    void setLoading(boolean loading) {
        this.loading = loading;
    }
}
