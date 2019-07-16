package ru.fitsme.android.presentation.fragments.iteminfo;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import ru.fitsme.android.domain.entities.clothes.ClothesItem;
import ru.fitsme.android.domain.interactors.clothes.IClothesInteractor;
import ru.fitsme.android.presentation.fragments.base.BaseViewModel;

public class ItemInfoViewModel extends BaseViewModel {

    private final IClothesInteractor clothesInteractor;

    private final MutableLiveData<ClotheInfo> itemLiveData = new MutableLiveData<>();

    public ItemInfoViewModel(@NonNull IClothesInteractor clothesInteractor) {
        this.clothesInteractor = clothesInteractor;
    }

    void init() {
//        itemLiveData.setValue(new ClotheInfo(ClotheInfo.State.LOADING));
//        addDisposable(clothesInteractor.getSingleClothesItem(index)
//                .subscribe(this::onItem, this::onError));
    }

    private void onError(Throwable throwable) {
//        itemLiveData.setValue(new ClotheInfo(ClotheInfo.State.ERROR));
    }

    private void onItem(@NonNull ClothesItem item) {
        itemLiveData.setValue(new ClotheInfo(item));
    }

    LiveData<ClotheInfo> getItemLiveData() {
        return itemLiveData;
    }
}
