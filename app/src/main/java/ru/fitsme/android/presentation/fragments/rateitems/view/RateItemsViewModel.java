package ru.fitsme.android.presentation.fragments.rateitems.view;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import io.reactivex.disposables.Disposable;
import ru.fitsme.android.domain.interactors.clothes.IClothesInteractor;

public class RateItemsViewModel extends ViewModel {
    private final IClothesInteractor clothesInteractor;

    private final MutableLiveData<Integer> indexLiveData = new MutableLiveData<>();
    private Disposable disposable;

    private int firstIndex;

    private RateItemsViewModel(@NonNull IClothesInteractor clothesInteractor) {
        this.clothesInteractor = clothesInteractor;

        disposable = clothesInteractor.getLastIndexSingle()
                .subscribe(indexLiveData::setValue);
    }

    public void likeClothesItem(boolean liked) {
        //TODO: доделать
        clothesInteractor.setLikeToClothesItem(firstIndex, liked)
                .subscribe(() -> {
                }, throwable -> {
                });

        indexLiveData.setValue(++firstIndex);
    }

    public LiveData<Integer> getIndexLiveData() {
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
