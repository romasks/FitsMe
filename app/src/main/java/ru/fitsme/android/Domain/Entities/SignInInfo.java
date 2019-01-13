package ru.fitsme.android.Domain.Entities;

public class SignInInfo {
    private String login;
    private String passwordHash;

    public SignInInfo(String login, String password) {
        this.login = login;
        this.passwordHash = convertToSha256(password);
    }

    public String getLogin() {
        return login;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    private String convertToSha256(String password) {
        return password + "_temp_hash";
    }
}
