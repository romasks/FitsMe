package ru.fitsme.android.data.frameworks.sharedpreferences;

import ru.fitsme.android.data.repositories.clothes.entity.ClotheSizeType;

public interface ISettingsStorage {
    ClotheSizeType getTopSizeType();

    void setTopSizeType(ClotheSizeType clotheSizeType);

    ClotheSizeType getBottomSizeType();

    void setBottomSizeType(ClotheSizeType clotheSizeType);

    Boolean getIsNeedShowSizeDialogForRateItemsTop();

    void setIsNeedShowSizeDialogForRateItemsTop(Boolean flag);

    Boolean getIsNeedShowSizeDialogForRateItemsBottom();

    void setIsNeedShowSizeDialogForRateItemsBottom(Boolean flag);
}
