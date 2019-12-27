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
import ru.fitsme.android.presentation.fragments.iteminfo.ClotheInfo;

public interface IClothesRepository {

    Single<ClotheInfo> likeItem(ClotheInfo clotheInfo, boolean liked);

    Single<List<ClotheInfo>> getClotheList();

    Single<SparseArray<ClotheSize>> getSizes();

    ClotheSizeType getSettingTopClothesSizeType();

    ClotheSizeType getSettingsBottomClothesSizeType();

    void setSettingsTopClothesSizeType(ClotheSizeType clothesSizeType);

    void setSettingsBottomClothesSizeType(ClotheSizeType clothesSizeType);

    void updateClotheBrands();

    LiveData<List<RoomBrand>> getBrandNames();

    void updateClotheColors();

    LiveData<List<RoomColor>> getClotheColors();

    void updateProductNames();

    LiveData<List<RoomProductName>> getClotheProductName();
}
