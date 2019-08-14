package ru.fitsme.android.domain.boundaries.clothes;

import android.util.SparseArray;

import java.util.List;

import io.reactivex.Single;
import ru.fitsme.android.data.repositories.clothes.entity.ClotheSizeType;
import ru.fitsme.android.domain.entities.clothes.ClotheSize;
import ru.fitsme.android.domain.entities.clothes.ClothesItem;
import ru.fitsme.android.presentation.fragments.iteminfo.ClotheInfo;

public interface IClothesRepository {

    Single<ClotheInfo> likeItem(ClothesItem clothesItem, boolean liked);

    Single<List<ClotheInfo>> getClotheList();

    Single<SparseArray<ClotheSize>> getSizes();

    ClotheSizeType getSettingTopClothesSizeType();

    ClotheSizeType getSettingsBottomClothesSizeType();

    void setSettingsTopClothesSizeType(ClotheSizeType clothesSizeType);

    void setSettingsBottomClothesSizeType(ClotheSizeType clothesSizeType);
}
