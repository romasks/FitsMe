package ru.fitsme.android.domain.interactors.clothes;

import java.util.List;

import androidx.lifecycle.LiveData;

import ru.fitsme.android.domain.interactors.BaseInteractor;
import ru.fitsme.android.presentation.fragments.iteminfo.ClotheInfo;

public interface IClothesInteractor extends BaseInteractor {

    void updateClothesList();

    void setLikeToClothesItem(ClotheInfo clotheInfo, boolean liked);

    LiveData<ClotheInfo> getClotheInfoLiveData();

    void setPreviousClotheInfo(ClotheInfo current);

    List<String> getProductNames();

    List<String> getBrands();

    List<String> getColors();
}
