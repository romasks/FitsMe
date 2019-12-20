package ru.fitsme.android.presentation.fragments.rateitems;

import androidx.lifecycle.LiveData;

import javax.inject.Inject;

import ru.fitsme.android.domain.interactors.clothes.IClothesInteractor;
import ru.fitsme.android.presentation.fragments.base.BaseViewModel;
import ru.fitsme.android.presentation.fragments.iteminfo.ClotheInfo;

public class RateItemsViewModel extends BaseViewModel {

    @Inject
    IClothesInteractor clothesInteractor;

    private LiveData<ClotheInfo> clotheInfoLiveData;


    public RateItemsViewModel() {
        inject(this);
    }

    @Override
    protected void init() {
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

    @Override
    public void onBackPressed() {
        navigation.finish();
    }

    public void onFilterClicked() {
        navigation.goToFilter();
    }
}
