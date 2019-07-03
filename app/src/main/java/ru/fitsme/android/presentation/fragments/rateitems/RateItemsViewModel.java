package ru.fitsme.android.presentation.fragments.rateitems;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import ru.fitsme.android.domain.interactors.clothes.IClothesInteractor;
import ru.fitsme.android.presentation.fragments.base.BaseViewModel;
import timber.log.Timber;

public class RateItemsViewModel extends BaseViewModel {

    private final IClothesInteractor clothesInteractor;
    private final MutableLiveData<RateItemsState> indexLiveData = new MutableLiveData<>();
    private int firstIndex;

    public RateItemsViewModel(@NonNull IClothesInteractor clothesInteractor) {
        this.clothesInteractor = clothesInteractor;
    }

    void init() {
        addDisposable(clothesInteractor.getLastIndexSingle()
                .subscribe(this::onIndex, this::onError));
    }

    void likeClothesItem(boolean liked, IOnSwipeListener.AnimationType animationType) {
        //TODO: можно вставить уведомление об лайке/дизлайке
        addDisposable(clothesInteractor.setLikeToClothesItem(firstIndex, liked)
                .subscribe(this::onLike, this::onError));
        indexLiveData.setValue(new RateItemsState(++firstIndex, animationType));
    }

    private void onIndex(Integer index) {
        firstIndex = index;
        RateItemsState rateItemsState = new RateItemsState(firstIndex,
                IOnSwipeListener.AnimationType.NONE);
        indexLiveData.setValue(rateItemsState);
    }

    private void onLike() {

    }

    private void onError(Throwable throwable) {
        Timber.tag(getClass().getName()).e(throwable);
    }

    LiveData<RateItemsState> getIndexLiveData() {
        return indexLiveData;
    }

    @Override
    public void onBackPressed() {

    }
}
