package ru.fitsme.android.presentation.fragments.iteminfo;

import ru.fitsme.android.domain.entities.exceptions.user.UserException;

public class ClotheInfo <T>  {
    public static final int RATE_ITEM_STATE = 0;
    public static final int FAVOURITES_NOT_IN_CART_STATE = 1;
    public static final int CART_STATE = 2;
    public static final int UNKNOWN_STATE = 3;
    public static final int FAVOURITES_IN_CART_STATE = 4;

    private T clothe;
    private UserException error;
    private int state;
    private ItemInfoFragment.Callback callback;

    public ClotheInfo (T clothe, int state) {
        this.clothe = clothe;
        if (state > 4 || state < 0) {
            throw new IllegalArgumentException("Wrong ClotheInfo state");
        } else {
            this.state = state;
        }
    }

    public ClotheInfo (T clothe) {
        this.clothe = clothe;
        state = UNKNOWN_STATE;
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

    public int getState() {
        return state;
    }

    public ItemInfoFragment.Callback getCallback() {
        return callback;
    }

    public void setCallback(ItemInfoFragment.Callback callback) {
        this.callback = callback;
    }
}
