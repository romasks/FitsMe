package ru.fitsme.android.presentation.fragments.rateitems;

import android.annotation.SuppressLint;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import javax.inject.Inject;
import javax.inject.Named;

import io.reactivex.Scheduler;
import ru.fitsme.android.domain.entities.clothes.LikeState;
import ru.fitsme.android.domain.interactors.clothes.IClothesInteractor;
import ru.fitsme.android.domain.interactors.profile.IProfileInteractor;
import ru.fitsme.android.presentation.fragments.base.BaseViewModel;
import ru.fitsme.android.presentation.fragments.iteminfo.ClotheInfo;

public class RateItemsViewModel extends BaseViewModel {

    @Inject
    IClothesInteractor clothesInteractor;
    @Inject
    IProfileInteractor profileInteractor;
    @Inject
    @Named("main") Scheduler mainThread;

    private LiveData<ClotheInfo> clotheInfoLiveData;
    private MutableLiveData<Boolean> filterIsChecked = new MutableLiveData<>();
    private LiveData<LikeState> likeStateLiveData;
    private LiveData<Boolean> returnIsEnabled = new MutableLiveData<>();
    private LiveData<Boolean> isNeedShowSizeDialogForTop;
    private LiveData<Boolean> isNeedShowSizeDialogForBottom;

    public RateItemsViewModel() {
        inject(this);
    }

    @Override
    protected void init() {
        clotheInfoLiveData = clothesInteractor.getClotheInfoLiveData();
        returnIsEnabled = clothesInteractor.getHasPreviousItem();
        likeStateLiveData = clothesInteractor.getLikeStateLiveData();
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

    @SuppressLint("CheckResult")
    public void onAfterCreateView() {
        clothesInteractor.isFiltersChecked()
            .observeOn(mainThread)
            .subscribe(result -> filterIsChecked.setValue(result));
        clothesInteractor.updateClothesList();
        isNeedShowSizeDialogForTop = clothesInteractor.getIsNeedShowSizeDialogForTop();
        isNeedShowSizeDialogForBottom = clothesInteractor.getIsNeedShowSizeDialogForBottom();
    }

    public LiveData<Boolean> getFilterIconLiveData() {
        return filterIsChecked;
    }

    public LiveData<Boolean> getReturnIconLiveData() {
        return returnIsEnabled;
    }

    public LiveData<LikeState> getLikeStateLiveData() {
        return likeStateLiveData;
    }

    public LiveData<Boolean> getIsNeedShowSizeDialogForTop() {
        return isNeedShowSizeDialogForTop;
    }

    public void setIsNeedShowSizeDialogForTop(Boolean flag){
        clothesInteractor.setIsNeedShowSizeDialogForTop(flag);
    }

    public LiveData<Boolean> getIsNeedShowSizeDialogForBottom() {
        return isNeedShowSizeDialogForBottom;
    }

    public void setIsNeedShowSizeDialogForBottom(Boolean flag){
        clothesInteractor.setIsNeedShowSizeDialogForBottom(flag);
    }

    public Boolean isItFirstStart(){
        return profileInteractor.isItFirstStart();
    }

    public void setFirstStartCompleted(){
        profileInteractor.setFirstStartCompleted();
    }
}
