package ru.fitsme.android.domain.boundaries.clothes;

import android.util.SparseArray;

import androidx.lifecycle.LiveData;

import java.util.List;

import io.reactivex.Single;
import ru.fitsme.android.data.frameworks.room.RoomBrand;
import ru.fitsme.android.data.frameworks.room.RoomColor;
import ru.fitsme.android.data.frameworks.room.RoomProductName;
import ru.fitsme.android.data.repositories.clothes.entity.ClotheSizeType;
import ru.fitsme.android.domain.entities.clothes.ClotheSize;
import ru.fitsme.android.domain.entities.clothes.FilterBrand;
import ru.fitsme.android.domain.entities.clothes.FilterColor;
import ru.fitsme.android.domain.entities.clothes.FilterProductName;
import ru.fitsme.android.presentation.fragments.iteminfo.ClotheInfo;

public interface IClothesRepository {

    Single<ClotheInfo> likeItem(ClotheInfo clotheInfo, boolean liked);

    Single<List<ClotheInfo>> getClotheList();

    Single<SparseArray<ClotheSize>> getSizes();

    ClotheSizeType getSettingTopClothesSizeType();

    ClotheSizeType getSettingsBottomClothesSizeType();

    void setSettingsTopClothesSizeType(ClotheSizeType clothesSizeType);

    void setSettingsBottomClothesSizeType(ClotheSizeType clothesSizeType);

    void updateClotheBrandList();

    LiveData<List<RoomBrand>> getBrandNames();

    void updateClotheColorList();

    LiveData<List<RoomColor>> getClotheColors();

    void updateProductNameList();

    LiveData<List<RoomProductName>> getClotheProductName();

    void updateProductName(FilterProductName filterProductName);

    void updateClotheBrand(FilterBrand filterBrand);

    void updateClotheColor(FilterColor filterColor);
}
