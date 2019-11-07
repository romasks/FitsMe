package ru.fitsme.android.domain.boundaries.auth;

import androidx.annotation.Nullable;

public interface ITextValidator {

    boolean checkLogin(@Nullable String login);

    boolean checkPassword(@Nullable String password);
}
