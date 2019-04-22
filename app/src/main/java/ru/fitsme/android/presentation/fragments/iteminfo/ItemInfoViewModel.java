package ru.fitsme.android.presentation.fragments.iteminfo;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import io.reactivex.disposables.Disposable;
import ru.fitsme.android.data.entities.response.clothes.ClothesItem;
import ru.fitsme.android.domain.interactors.clothes.IClothesInteractor;

public class ItemInfoViewModel extends ViewModel {
    private final IClothesInteractor clothesInteractor;
    private final int index;

    private final MutableLiveData<ItemInfoState> itemLiveData = new MutableLiveData<>();
    private final Disposable disposable;

    private ItemInfoViewModel(@NonNull IClothesInteractor clothesInteractor, int index) {
        this.clothesInteractor = clothesInteractor;
        this.index = index;

        itemLiveData.setValue(new ItemInfoState(ItemInfoState.State.LOADING));

        disposable = clothesInteractor.getSingleClothesItem(index)
                .subscribe(this::onItem, this::onError);
    }

    private void onError(Throwable throwable) {
        itemLiveData.setValue(new ItemInfoState(ItemInfoState.State.ERROR));
    }

    private void onItem(@NonNull ClothesItem item) {
        itemLiveData.setValue(new ItemInfoState(item));
    }

    @Override
    protected void onCleared() {
        super.onCleared();

        disposable.dispose();
    }

    public LiveData<ItemInfoState> getItemLiveData() {
        return itemLiveData;
    }

    public static class Factory implements ViewModelProvider.Factory {
        private final IClothesInteractor clothesInteractor;
        private final int index;

        public Factory(@NonNull IClothesInteractor clothesInteractor, int index) {
            this.clothesInteractor = clothesInteractor;
            this.index = index;
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            return (T) new ItemInfoViewModel(clothesInteractor, index);
        }
    }
}
