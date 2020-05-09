package ru.fitsme.android.data.frameworks.sharedpreferences;

import android.content.Context;
import android.content.SharedPreferences;

import javax.inject.Inject;

import ru.fitsme.android.data.repositories.clothes.entity.ClotheSizeType;

public class SettingsStorage implements ISettingsStorage {

    private static final String PREF_NAME = "settingsPref";
    private static final String TOP_SIZE_TYPE_KEY = "topSizeType";
    private static final String BOTTOM_SIZE_TYPE_KEY = "bottomSizeType";
    private static final String IS_NEED_SHOW_SIZE_DIALOG_FOR_RATE_ITEMS_TOP = "showSDforRateTop";
    private static final String IS_NEED_SHOW_SIZE_DIALOG_FOR_RATE_ITEMS_BOTTOM = "showSDforRateBot";
    private static final String IS_IT_FIRST_START = "firstStart";

    private SharedPreferences sharedPreferences;

    @Inject
    SettingsStorage(Context appContext) {
        sharedPreferences = appContext.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    @Override
    public ClotheSizeType getTopSizeType(){
        int value = sharedPreferences.getInt(TOP_SIZE_TYPE_KEY, ClotheSizeType.Undefined.getValue());
        return getClotheSizeType(value);
    }

    @Override
    public void setTopSizeType(ClotheSizeType clotheSizeType){
        int value = clotheSizeType.getValue();
        sharedPreferences.edit()
                .putInt(TOP_SIZE_TYPE_KEY, value)
                .apply();
    }

    @Override
    public ClotheSizeType getBottomSizeType(){
        int value = sharedPreferences.getInt(BOTTOM_SIZE_TYPE_KEY, ClotheSizeType.Undefined.getValue());
        return getClotheSizeType(value);
    }

    @Override
    public void setBottomSizeType(ClotheSizeType clotheSizeType){
        int value = clotheSizeType.getValue();
        sharedPreferences.edit()
                .putInt(BOTTOM_SIZE_TYPE_KEY, value)
                .apply();
    }

    @Override
    public Boolean getIsNeedShowSizeDialogForRateItemsTop() {
        return sharedPreferences
                .getBoolean(IS_NEED_SHOW_SIZE_DIALOG_FOR_RATE_ITEMS_TOP, true);
    }

    @Override
    public void setIsNeedShowSizeDialogForRateItemsTop(Boolean flag){
        sharedPreferences.edit()
                .putBoolean(IS_NEED_SHOW_SIZE_DIALOG_FOR_RATE_ITEMS_TOP, flag)
                .apply();
    }

    private ClotheSizeType getClotheSizeType(int value) {
        for (int i = 0; i < ClotheSizeType.values().length; i++) {
            if (ClotheSizeType.values()[i].getValue() == value) {
                return ClotheSizeType.values()[i];
            }
        }
        throw new IndexOutOfBoundsException("Value out of ClotheSizeType bounds");
    }

    @Override
    public void setIsNeedShowSizeDialogForRateItemsBottom(Boolean flag){
        sharedPreferences.edit()
                .putBoolean(IS_NEED_SHOW_SIZE_DIALOG_FOR_RATE_ITEMS_BOTTOM, flag)
                .apply();
    }

    @Override
    public Boolean isItFirstStart() {
        return sharedPreferences.getBoolean(IS_IT_FIRST_START, true);
    }

    @Override
    public void setFirstStartCompleted(){
        sharedPreferences.edit()
                .putBoolean(IS_IT_FIRST_START, false)
                .apply();
    }

    @Override
    public Boolean getIsNeedShowSizeDialogForRateItemsBottom() {
        return sharedPreferences
                .getBoolean(IS_NEED_SHOW_SIZE_DIALOG_FOR_RATE_ITEMS_BOTTOM, true);
    }
}
