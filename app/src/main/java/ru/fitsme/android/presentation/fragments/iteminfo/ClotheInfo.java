package ru.fitsme.android.presentation.fragments.iteminfo;

import ru.fitsme.android.domain.entities.exceptions.user.UserException;

public class ClotheInfo <T>  {
    private T clothe;
    private UserException error;

    public ClotheInfo (T clothe) {
        this.clothe = clothe;
    }

    public ClotheInfo(UserException error) {
        this.error = error;
    }

    public T getClothe() {
        return clothe;
    }

    public UserException getError() {
        return error;
    }
}
