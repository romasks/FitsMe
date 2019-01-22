package ru.fitsme.android.domain.entities.signinup;

import android.support.annotation.Nullable;

public class AutoSignInInfo {
    private SignInInfo signInInfo;
    private boolean auto;

    public AutoSignInInfo(@Nullable SignInInfo signInInfo, boolean auto) {
        this.signInInfo = signInInfo;
        this.auto = signInInfo != null && auto;
    }

    @Nullable
    public SignInInfo getSignInInfo() {
        return signInInfo;
    }

    public boolean isAuto() {
        return auto;
    }

    public void disableAuto() {
        auto = false;
    }
}
