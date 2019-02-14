package ru.fitsme.android.domain.entities.signinup;

import com.google.gson.annotations.SerializedName;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;

public class SignInInfo {
    private String login;
    @SerializedName("password")
    private String passwordHash;

    private SignInInfo(String login) {
        this.login = login;
    }

    public SignInInfo(String login, String password) {
        this.login = login;
        this.passwordHash = convertToSha256(password);
    }

    public static SignInInfo create(String login, String passwordHash) {
        SignInInfo signInInfo = new SignInInfo(login);
        signInInfo.passwordHash = passwordHash;
        return signInInfo;
    }

    private static String convertToSha256(String data) {
        return new String(Hex.encodeHex(DigestUtils.sha256(data)));
    }

    public String getLogin() {
        return login;
    }

    public String getPasswordHash() {
        return passwordHash;
    }
}
