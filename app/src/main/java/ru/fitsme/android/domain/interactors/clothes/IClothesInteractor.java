package ru.fitsme.android.domain.interactors.clothes;

import androidx.lifecycle.LiveData;

import java.util.List;

import io.reactivex.Single;
import ru.fitsme.android.domain.entities.clothes.FilterBrand;
import ru.fitsme.android.domain.entities.clothes.FilterColor;
import ru.fitsme.android.domain.entities.clothes.FilterProductName;
import ru.fitsme.android.domain.entities.clothes.LikeState;
import ru.fitsme.android.domain.interactors.BaseInteractor;
import ru.fitsme.android.presentation.fragments.iteminfo.ClotheInfo;

public interface IClothesInteractor extends BaseInteractor {

    void updateClothesList();

    void setLikeToClothesItem(ClotheInfo clotheInfo, boolean liked);

    LiveData<ClotheInfo> getClotheInfoLiveData();

    PreviousClotheInfoList getPreviousClotheInfoList();

    LiveData<Boolean> getHasPreviousItem();

    LiveData<LikeState> getLikeStateLiveData();

    void setPreviousClotheInfo(ClotheInfo current);

    LiveData<List<FilterProductName>> getProductNames();

    LiveData<List<FilterBrand>> getBrands();

    LiveData<List<FilterColor>> getColors();

    void setFilterProductName(FilterProductName filterProductName);

    void setFilterBrand(FilterBrand filterBrand);

    void setFilterColor(FilterColor filterColor);

    void resetCheckedFilters();

    Single<Boolean> isFiltersChecked();

    LiveData<Boolean> getIsNeedShowSizeDialogForTop();

    void setIsNeedShowSizeDialogForTop(Boolean flag);

    LiveData<Boolean> getIsNeedShowSizeDialogForBottom();

    void setIsNeedShowSizeDialogForBottom(Boolean flag);
}
