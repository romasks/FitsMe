package ru.fitsme.android.presentation.fragments.signinup.entities;

public class FieldsStateBuilder {
    private FieldsState fieldsState;
    private boolean clear;

    public FieldsStateBuilder() {
        fieldsState = new FieldsState();
        clear = true;
    }

    public FieldsStateBuilder setLoginError(String loginError) {
        fieldsState.setLoginError(loginError);
        clear = false;
        return this;
    }

    public FieldsStateBuilder setPasswordError(String passwordError) {
        fieldsState.setPasswordError(passwordError);
        clear = false;
        return this;
    }

    public FieldsStateBuilder setCommonError(String commonError) {
        fieldsState.setCommonError(commonError);
        clear = false;
        return this;
    }

    public FieldsStateBuilder setLoading(boolean loading) {
        fieldsState.setLoading(loading);
        return this;
    }

    public FieldsState complete() {
        return fieldsState;
    }

    public boolean isClear() {
        return clear;
    }
}
