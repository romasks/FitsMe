package ru.fitsme.android.presentation.fragments.rateitems;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import ru.fitsme.android.domain.entities.clothes.ClothesItem;
import ru.fitsme.android.domain.interactors.clothes.IClothesInteractor;
import ru.fitsme.android.presentation.fragments.base.BaseViewModel;
import ru.fitsme.android.presentation.fragments.iteminfo.ClotheInfo;
import timber.log.Timber;

public class RateItemsViewModel extends BaseViewModel {

    private final IClothesInteractor clothesInteractor;
    private LiveData<List<ClotheInfo>> clotheInfoListLiveData;
//    private IOnSwipeListener.AnimationType animationType;

    public RateItemsViewModel(@NonNull IClothesInteractor clothesInteractor) {
        this.clothesInteractor = clothesInteractor;
    }

    void init() {
        clotheInfoListLiveData = clothesInteractor.getClotheInfoListLiveData();
    }
//
//    private void onNext(ClotheInfo clotheInfo) {
//        RateItemsState rateItemsState = new RateItemsState(clotheInfo, animationType);
//        clotheInfoListLiveData.setValue(rateItemsState);
//    }

    void likeClothesItem(boolean liked) {
//        this.animationType = animationType;
//        RateItemsState rateItemsState = clotheInfoListLiveData.getValue();
//        ClothesItem clothesItem = (ClothesItem) rateItemsState.getClotheInfo().getClothe();
//        addDisposable(clothesInteractor.setLikeToClothesItem(clothesItem, liked)
//                .subscribe(clotheInfo -> clothesInteractor.getNext(), Timber::e));
    }

    LiveData<List<ClotheInfo>> getClotheInfoListLiveData() {
        return clotheInfoListLiveData;
    }
}
