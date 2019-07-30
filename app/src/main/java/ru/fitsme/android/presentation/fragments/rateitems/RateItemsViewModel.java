package ru.fitsme.android.presentation.fragments.rateitems;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import ru.fitsme.android.domain.entities.clothes.ClothesItem;
import ru.fitsme.android.domain.interactors.clothes.IClothesInteractor;
import ru.fitsme.android.presentation.fragments.base.BaseViewModel;
import ru.fitsme.android.presentation.fragments.iteminfo.ClotheInfo;
import timber.log.Timber;

public class RateItemsViewModel extends BaseViewModel {

    private final IClothesInteractor clothesInteractor;
    private final MutableLiveData<RateItemsState> rateItemsStateLiveData = new MutableLiveData<>();
    private IOnSwipeListener.AnimationType animationType;

    public RateItemsViewModel(@NonNull IClothesInteractor clothesInteractor) {
        this.clothesInteractor = clothesInteractor;
    }

    void init() {
        animationType = IOnSwipeListener.AnimationType.SIMPLE;
        addDisposable(clothesInteractor.getItemInfoState()
                .subscribe(this::onNext, this::onError));
    }

    private void onNext(ClotheInfo clotheInfo) {
        RateItemsState rateItemsState = new RateItemsState(clotheInfo, animationType);
        rateItemsStateLiveData.setValue(rateItemsState);
    }

    void likeClothesItem(boolean liked, IOnSwipeListener.AnimationType animationType) {
        this.animationType = animationType;
        RateItemsState rateItemsState = rateItemsStateLiveData.getValue();
        if (rateItemsState != null){
            clothesInteractor.setLikeToClothesItem(
                    (ClothesItem) rateItemsState.getClotheInfo().getClothe(), liked)
                    .subscribe(clotheInfo -> {
                        clothesInteractor.getNext();
                    }, Timber::e);
        }
    }

    private void onError(Throwable throwable) {
        Timber.tag(getClass().getName()).e(throwable);
    }

    LiveData<RateItemsState> getRateItemsStateLiveData() {
        return rateItemsStateLiveData;
    }
}
