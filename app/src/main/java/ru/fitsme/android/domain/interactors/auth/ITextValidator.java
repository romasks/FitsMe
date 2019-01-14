package ru.fitsme.android.domain.interactors.auth;

public interface ITextValidator {

    boolean checkLogin(String login);

    boolean checkPassword(String password);
}
