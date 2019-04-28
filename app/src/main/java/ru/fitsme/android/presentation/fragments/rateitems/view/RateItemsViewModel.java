package ru.fitsme.android.presentation.fragments.rateitems.view;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import io.reactivex.disposables.CompositeDisposable;
import ru.fitsme.android.domain.interactors.clothes.IClothesInteractor;

public class RateItemsViewModel extends ViewModel {
    private final IClothesInteractor clothesInteractor;

    private final MutableLiveData<RateItemsState> indexLiveData = new MutableLiveData<>();
    private CompositeDisposable disposable;

    private int firstIndex;

    private RateItemsViewModel(@NonNull IClothesInteractor clothesInteractor) {
        this.clothesInteractor = clothesInteractor;
    }

    void init() {
        disposable = new CompositeDisposable();
        disposable.add(
                clothesInteractor.getLastIndexSingle()
                        .subscribe(index -> {
                            firstIndex = index;
                            RateItemsState rateItemsState = new RateItemsState(firstIndex,
                                    IOnSwipeListener.AnimationType.NONE);
                            indexLiveData.setValue(rateItemsState);
                        })
        );
    }

    void likeClothesItem(boolean liked, IOnSwipeListener.AnimationType animationType) {
        //TODO: можно вставить уведомление об лайке/дизлайке
        disposable.add(
                clothesInteractor.setLikeToClothesItem(firstIndex, liked)
                        .subscribe(() -> {
                        }, throwable -> {
                        })
        );

        indexLiveData.setValue(new RateItemsState(++firstIndex, animationType));
    }

    LiveData<RateItemsState> getIndexLiveData() {
        return indexLiveData;
    }

    @Override
    protected void onCleared() {
        super.onCleared();

        disposable.dispose();
    }

    static public class Factory implements ViewModelProvider.Factory {
        private final IClothesInteractor clothesInteractor;

        public Factory(@NonNull IClothesInteractor clothesInteractor) {
            this.clothesInteractor = clothesInteractor;
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            return (T) new RateItemsViewModel(clothesInteractor);
        }
    }
}
