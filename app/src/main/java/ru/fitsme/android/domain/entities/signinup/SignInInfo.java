package ru.fitsme.android.domain.entities.signinup;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import ru.fitsme.android.domain.entities.exceptions.ConvertHashException;

public class SignInInfo {
    private String login;
    private String passwordHash;

    public SignInInfo(String login, String password) throws ConvertHashException {
        this.login = login;
        this.passwordHash = convertToSha256(password);
    }

    private static String convertToSha256(String data) throws ConvertHashException {
        try {
            return Arrays.toString(MessageDigest.getInstance("SHA-512").digest(data.getBytes()));
        } catch (NoSuchAlgorithmException e) {
            throw new ConvertHashException();
        }
    }

    public String getLogin() {
        return login;
    }

    public String getPasswordHash() {
        return passwordHash;
    }
}
