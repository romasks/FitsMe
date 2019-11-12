package ru.fitsme.android.presentation.fragments.rateitems;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import ru.fitsme.android.domain.interactors.clothes.IClothesInteractor;
import ru.fitsme.android.presentation.fragments.base.BaseViewModel;
import ru.fitsme.android.presentation.fragments.iteminfo.ClotheInfo;

public class RateItemsViewModel extends BaseViewModel {

    private final IClothesInteractor clothesInteractor;
    private LiveData<ClotheInfo> clotheInfoLiveData;


    public RateItemsViewModel(@NonNull IClothesInteractor clothesInteractor) {
        this.clothesInteractor = clothesInteractor;
    }

    void init() {
        clotheInfoLiveData = clothesInteractor.getClotheInfoLiveData();
    }

    void likeClothesItem(ClotheInfo clotheInfo, boolean liked) {
        clothesInteractor.setLikeToClothesItem(clotheInfo, liked);
    }

    LiveData<ClotheInfo> getClotheInfoLiveData() {
        return clotheInfoLiveData;
    }

    public void onReturnClicked(ClotheInfo clotheInfo) {
        clothesInteractor.setPreviousClotheInfo(clotheInfo);
    }
}
