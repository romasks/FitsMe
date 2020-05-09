package ru.fitsme.android.data.frameworks.sharedpreferences;

import ru.fitsme.android.data.repositories.clothes.entity.ClotheSizeType;

public interface ISettingsStorage {
    ClotheSizeType getTopSizeType();

    void setTopSizeType(ClotheSizeType clotheSizeType);

    ClotheSizeType getBottomSizeType();

    void setBottomSizeType(ClotheSizeType clotheSizeType);

    Boolean getIsNeedShowSizeDialogForRateItemsTop();

    void setIsNeedShowSizeDialogForRateItemsTop(Boolean flag);

    void setFirstStartCompleted();

    Boolean getIsNeedShowSizeDialogForRateItemsBottom();

    void setIsNeedShowSizeDialogForRateItemsBottom(Boolean flag);

    Boolean isItFirstStart();
}
