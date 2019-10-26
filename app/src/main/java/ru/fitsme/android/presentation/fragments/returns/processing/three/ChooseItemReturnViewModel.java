package ru.fitsme.android.presentation.fragments.returns.processing.three;

import android.arch.lifecycle.LiveData;
import android.arch.paging.PagedList;
import android.databinding.ObservableBoolean;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import ru.fitsme.android.domain.entities.clothes.ClothesItem;
import ru.fitsme.android.domain.entities.returns.ReturnsItem;
import ru.fitsme.android.domain.interactors.returns.IReturnsInteractor;
import ru.fitsme.android.presentation.fragments.base.BaseViewModel;

public class ChooseItemReturnViewModel extends BaseViewModel {

    private final IReturnsInteractor returnsInteractor;

    public ObservableBoolean isLoading = new ObservableBoolean(true);
    public List<ClothesItem> clothesList = new ArrayList<>();

    public ChooseItemReturnViewModel(@NotNull IReturnsInteractor returnsInteractor) {
        this.returnsInteractor = returnsInteractor;
        inject(this);
    }

    void init() {
        isLoading.set(false);
        clothesList.add(new ClothesItem("Adidas", "Кроссовки", 325));
        clothesList.add(new ClothesItem("Dolce Gabana", "Платье", 2130));
        clothesList.add(new ClothesItem("Nike", "Кепка", 210));
        clothesList.add(new ClothesItem("Collins", "Джинсы", 685));
    }

    LiveData<PagedList<ReturnsItem>> getPageLiveData() {
        return returnsInteractor.getPagedListLiveData();
    }

    public void goToReturnsIndicateNumber() {
        navigation.goToReturnsIndicateNumber();
    }

    public void backToReturnsChooseOrder() {
        navigation.backToReturnsChooseOrder();
    }
}
