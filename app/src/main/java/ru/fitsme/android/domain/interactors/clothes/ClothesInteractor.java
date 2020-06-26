package ru.fitsme.android.domain.interactors.clothes;

import android.annotation.SuppressLint;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import io.reactivex.Scheduler;
import io.reactivex.Single;
import ru.fitsme.android.data.frameworks.room.RoomBrand;
import ru.fitsme.android.data.frameworks.room.RoomColor;
import ru.fitsme.android.data.frameworks.room.RoomProductName;
import ru.fitsme.android.domain.boundaries.clothes.IClothesRepository;
import ru.fitsme.android.domain.entities.clothes.FilterBrand;
import ru.fitsme.android.domain.entities.clothes.FilterColor;
import ru.fitsme.android.domain.entities.clothes.FilterProductName;
import ru.fitsme.android.domain.entities.clothes.LikeState;
import ru.fitsme.android.domain.entities.clothes.LikedClothesItem;
import ru.fitsme.android.presentation.fragments.iteminfo.ClotheInfo;
import timber.log.Timber;

@Singleton
public class ClothesInteractor implements IClothesInteractor {

    private final IClothesRepository clothesRepository;
    private final Scheduler mainThread;

    private MutableLiveData<ClotheInfo> clotheInfoMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<Boolean> isNeedShowSizeDialogForTop = new MutableLiveData<>();
    private MutableLiveData<Boolean> isNeedShowSizeDialogForBottom = new MutableLiveData<>();
    private MutableLiveData<Boolean> hasPreviousItem = new MutableLiveData<>();
    private MutableLiveData<LikeState> likeStateLiveData = new MutableLiveData<>();

    private LinkedList<ClotheInfo> clotheInfoList;
    private PreviousClotheInfoList previousItemInfoList = new PreviousClotheInfoList();
    private boolean isLikeRequestInProgress = false;

    @Inject
    ClothesInteractor(IClothesRepository clothesRepository, @Named("main") Scheduler mainThread) {
        this.clothesRepository = clothesRepository;
        this.mainThread = mainThread;

        clothesRepository.updateClotheBrandList();
        clothesRepository.updateClotheColorList();
        clothesRepository.updateProductNameList();

        hasPreviousItem.setValue(false);
        likeStateLiveData.setValue(LikeState.INITIAL);
    }

    @SuppressLint("CheckResult")
    @Override
    public void updateClothesList() {
        isLikeRequestInProgress = false;
        clothesRepository.getClotheList()
                .observeOn(mainThread)
                .subscribe(clotheInfoList -> {
                    this.clotheInfoList = (LinkedList<ClotheInfo>) clotheInfoList;
                    clotheInfoMutableLiveData.setValue(this.clotheInfoList.pollFirst());
                }, Timber::e);
    }

    private void setNextClotheInfo() {
        ClotheInfo clotheInfo = clotheInfoList.pollFirst();
        if (clotheInfo != null) {
            clotheInfoMutableLiveData.setValue(clotheInfo);
        } else {
            updateClothesList();
        }
    }

    @SuppressLint("CheckResult")
    @Override
    public void setPreviousClotheInfo(ClotheInfo current) {
        if (previousItemInfoList.hasPrevious()) {
            clotheInfoList.addFirst(current);
            ClotheInfo clotheInfo = previousItemInfoList.pollLast();
            clotheInfoMutableLiveData.setValue(clotheInfo);

            int clotheId = ((LikedClothesItem) clotheInfo.getClothe()).getId();
            clothesRepository.returnItemFromViewed(clotheId)
                    .observeOn(mainThread)
                    .subscribe(response -> {
                        updateClothesList();
                        hasPreviousItem.setValue(previousItemInfoList.hasPrevious());
                    }, Timber::e);
        } else {
            hasPreviousItem.setValue(false);
        }
    }

    @Override
    public PreviousClotheInfoList getPreviousClotheInfoList() {
        return previousItemInfoList;
    }

    @Override
    public LiveData<Boolean> getHasPreviousItem() {
        return hasPreviousItem;
    }

    @Override
    public LiveData<LikeState> getLikeStateLiveData() {
        return likeStateLiveData;
    }

    @SuppressLint("CheckResult")
    @Override
    public void setLikeToClothesItem(ClotheInfo clotheInfo, boolean liked) {
        likeStateLiveData.setValue(LikeState.LOADING);
        if (!isLikeRequestInProgress) {
            isLikeRequestInProgress = true;
            clothesRepository.likeItem(clotheInfo, liked)
                    .observeOn(mainThread)
                    .subscribe(callback -> {
                        isLikeRequestInProgress = false;
                        setNextClotheInfo();
                        previousItemInfoList.add(callback);

                        hasPreviousItem.setValue(true);
                        likeStateLiveData.setValue(LikeState.SUCCESS);
                    }, throwable -> {
                        likeStateLiveData.setValue(LikeState.ERROR);
                        Timber.e(throwable);
                    });
        }
    }

    @Override
    public LiveData<ClotheInfo> getClotheInfoLiveData() {
        return clotheInfoMutableLiveData;
    }

    @Override
    public LiveData<List<FilterProductName>> getProductNames() {
        return Transformations.map(clothesRepository.getClotheProductName(), roomProductNamesList -> {
            List<FilterProductName> output = new ArrayList<>();
            for (RoomProductName productName : roomProductNamesList) {
                output.add(new FilterProductName(productName));
            }
            return output;
        });
    }

    @Override
    public LiveData<List<FilterBrand>> getBrands() {
        return Transformations.map(clothesRepository.getBrandNames(), roomBrandsList -> {
            List<FilterBrand> output = new ArrayList<>();
            for (RoomBrand brand : roomBrandsList) {
                output.add(new FilterBrand(brand));
            }
            return output;
        });
    }

    @Override
    public LiveData<List<FilterColor>> getColors() {
        return Transformations.map(clothesRepository.getClotheColors(), roomColorsList -> {
            List<FilterColor> output = new ArrayList<>();
            for (RoomColor color : roomColorsList) {
                output.add(new FilterColor(color));
            }
            return output;
        });
    }

    @Override
    public void setFilterProductName(FilterProductName filterProductName) {
        clothesRepository.updateProductName(filterProductName);
    }

    @Override
    public void setFilterBrand(FilterBrand filterBrand) {
        clothesRepository.updateClotheBrand(filterBrand);
    }

    @Override
    public void setFilterColor(FilterColor filterColor) {
        clothesRepository.updateClotheColor(filterColor);
    }

    @Override
    public void resetCheckedFilters() {
        clothesRepository.resetCheckedFilters();
    }

    @Override
    public Single<Boolean> isFiltersChecked() {
        return clothesRepository.isFiltersChecked();
    }

    @Override
    public LiveData<Boolean> getIsNeedShowSizeDialogForTop() {
        boolean flag = clothesRepository.getIsNeedShowSizeDialogTop();
        isNeedShowSizeDialogForTop.setValue(flag);
        return isNeedShowSizeDialogForTop;
    }

    @Override
    public void setIsNeedShowSizeDialogForTop(Boolean flag) {
        isNeedShowSizeDialogForTop.setValue(flag);
        clothesRepository.setIsNeedShowSizeDialogTop(flag);
        if (flag) {
            updateClothesList();
        }
    }

    @Override
    public LiveData<Boolean> getIsNeedShowSizeDialogForBottom() {
        boolean flag = clothesRepository.getIsNeedShowSizeDialogBottom();
        isNeedShowSizeDialogForBottom.setValue(flag);
        return isNeedShowSizeDialogForBottom;
    }

    @Override
    public void setIsNeedShowSizeDialogForBottom(Boolean flag) {
        isNeedShowSizeDialogForBottom.setValue(flag);
        clothesRepository.setIsNeedShowSizeDialogBottom(flag);
        if (flag) {
            updateClothesList();
        }
    }
}
