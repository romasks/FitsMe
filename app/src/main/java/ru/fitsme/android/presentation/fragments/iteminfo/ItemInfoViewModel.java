package ru.fitsme.android.presentation.fragments.iteminfo;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import ru.fitsme.android.domain.entities.clothes.ClothesItem;
import ru.fitsme.android.domain.interactors.clothes.IClothesInteractor;
import ru.fitsme.android.presentation.fragments.base.BaseViewModel;

public class ItemInfoViewModel extends BaseViewModel {

    private final IClothesInteractor clothesInteractor;
    private final int index;

    private final MutableLiveData<ItemInfoState> itemLiveData = new MutableLiveData<>();

    public ItemInfoViewModel(@NonNull IClothesInteractor clothesInteractor, int index) {
        this.clothesInteractor = clothesInteractor;
        this.index = index;
    }

    void init() {
        itemLiveData.setValue(new ItemInfoState(ItemInfoState.State.LOADING));
        addDisposable(clothesInteractor.getSingleClothesItem(index)
                .subscribe(this::onItem, this::onError));
    }

    private void onError(Throwable throwable) {
        itemLiveData.setValue(new ItemInfoState(ItemInfoState.State.ERROR));
    }

    private void onItem(@NonNull ClothesItem item) {
        itemLiveData.setValue(new ItemInfoState(item));
    }

    LiveData<ItemInfoState> getItemLiveData() {
        return itemLiveData;
    }
}
