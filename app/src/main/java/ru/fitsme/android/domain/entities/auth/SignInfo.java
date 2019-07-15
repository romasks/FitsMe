package ru.fitsme.android.domain.entities.auth;

import com.google.gson.annotations.SerializedName;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;

public class SignInfo {
    private String login;
    @SerializedName("password")
    private String passwordHash;

    private SignInfo(String login) {
        this.login = login;
    }

    public SignInfo(String login, String password) {
        this.login = login;
        this.passwordHash = convertToSha256(password);
    }

    public static SignInfo create(String login, String passwordHash) {
        SignInfo signInfo = new SignInfo(login);
        signInfo.passwordHash = passwordHash;
        return signInfo;
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
