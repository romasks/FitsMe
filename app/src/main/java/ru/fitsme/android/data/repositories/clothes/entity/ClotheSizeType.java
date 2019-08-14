package ru.fitsme.android.data.repositories.clothes.entity;

import ru.fitsme.android.R;
import ru.fitsme.android.app.App;

public enum ClotheSizeType {

    Undefined(0, App.getInstance().getString(R.string.undefined_size_type)),
    International(1, App.getInstance().getString(R.string.international_sie_type)),
    Russia(2, App.getInstance().getString(R.string.russia_size_type)),
    Europe(3, App.getInstance().getString(R.string.europe_size_type)),
    France(4, App.getInstance().getString(R.string.france_size_type)),
    Italy(5, App.getInstance().getString(R.string.italy_size_type)),
    USA(6, App.getInstance().getString(R.string.usa_size_type)),
    UK(7, App.getInstance().getString(R.string.uk_size_type));

    private final int value;
    private final String string;

    ClotheSizeType(int value, String string){
        this.value = value;
        this.string = string;
    }

    public int getValue() {
        return value;
    }
    public String getString(){return string;}
}
