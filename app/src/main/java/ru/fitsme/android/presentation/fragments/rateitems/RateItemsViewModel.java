package ru.fitsme.android.presentation.fragments.rateitems;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import javax.inject.Inject;
import javax.inject.Named;

import io.reactivex.Scheduler;
import ru.fitsme.android.domain.interactors.clothes.IClothesInteractor;
import ru.fitsme.android.presentation.common.livedata.NonNullLiveData;
import ru.fitsme.android.presentation.fragments.base.BaseViewModel;
import ru.fitsme.android.presentation.fragments.iteminfo.ClotheInfo;

public class RateItemsViewModel extends BaseViewModel {

    @Inject
    IClothesInteractor clothesInteractor;
    @Inject
    @Named("main") Scheduler mainThread;

    private LiveData<ClotheInfo> clotheInfoLiveData;
    private MutableLiveData<Boolean> filterIsChecked = new MutableLiveData<>();


    public RateItemsViewModel() {
        inject(this);
    }

    @Override
    protected void init() {
        clotheInfoLiveData = clothesInteractor.getClotheInfoLiveData();
        filterIsChecked.setValue(false);
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

    public void onAfterCreateView() {
        clothesInteractor.isFiltersChecked()
            .observeOn(mainThread)
            .subscribe(result -> {
                filterIsChecked.setValue(result);
            });
    }

    public LiveData<Boolean> getFilterIconLiveData() {
        return filterIsChecked;
    }
}
