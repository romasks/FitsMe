package ru.fitsme.android.data.repositories.clothes;

import android.annotation.SuppressLint;
import android.util.SparseArray;

import androidx.lifecycle.LiveData;

import java.util.LinkedList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import io.reactivex.Completable;
import io.reactivex.Scheduler;
import io.reactivex.Single;
import ru.fitsme.android.R;
import ru.fitsme.android.app.App;
import ru.fitsme.android.data.frameworks.retrofit.WebLoaderNetworkChecker;
import ru.fitsme.android.data.frameworks.room.RoomBrand;
import ru.fitsme.android.data.frameworks.room.RoomColor;
import ru.fitsme.android.data.frameworks.room.RoomProductName;
import ru.fitsme.android.data.frameworks.room.dao.BrandsDao;
import ru.fitsme.android.data.frameworks.room.dao.ColorsDao;
import ru.fitsme.android.data.frameworks.room.dao.ProductNamesDao;
import ru.fitsme.android.data.frameworks.sharedpreferences.ISettingsStorage;
import ru.fitsme.android.data.repositories.ErrorRepository;
import ru.fitsme.android.data.repositories.clothes.entity.ClotheSizeType;
import ru.fitsme.android.data.repositories.clothes.entity.ClothesPage;
import ru.fitsme.android.domain.boundaries.clothes.IClothesRepository;
import ru.fitsme.android.domain.entities.clothes.ClotheSize;
import ru.fitsme.android.domain.entities.clothes.ClothesItem;
import ru.fitsme.android.domain.entities.clothes.FilterBrand;
import ru.fitsme.android.domain.entities.clothes.FilterColor;
import ru.fitsme.android.domain.entities.clothes.FilterProductName;
import ru.fitsme.android.domain.entities.clothes.LikedClothesItem;
import ru.fitsme.android.domain.entities.exceptions.user.UserException;
import ru.fitsme.android.presentation.fragments.iteminfo.ClotheInfo;
import timber.log.Timber;

public class ClothesRepository implements IClothesRepository {

    private final WebLoaderNetworkChecker webLoader;
    private final ISettingsStorage storage;
    private Scheduler workThread;
    private Scheduler mainThread;

    @Inject
    BrandsDao brandsDao;

    @Inject
    ColorsDao colorsDao;

    @Inject
    ProductNamesDao productNamesDao;

    @Inject
    ClothesRepository(WebLoaderNetworkChecker webLoader,
                      ISettingsStorage storage,
                      @Named("work") Scheduler workThread,
                      @Named("main") Scheduler mainThread) {
        this.webLoader = webLoader;
        this.storage = storage;
        this.workThread = workThread;
        this.mainThread = mainThread;
    }

    @Override
    public Single<ClotheInfo> likeItem(ClotheInfo clotheInfo, boolean liked) {
        ClothesItem clothesItem;
        if (clotheInfo.getClothe() instanceof ClothesItem) {
            clothesItem = (ClothesItem) clotheInfo.getClothe();
        } else if (clotheInfo.getClothe() instanceof LikedClothesItem) {
            LikedClothesItem likedClothesItem = (LikedClothesItem) clotheInfo.getClothe();
            clothesItem = likedClothesItem.getClothe();
        } else {
            throw new TypeNotPresentException(clotheInfo.toString(), null);
        }
        return Single.create(emitter -> webLoader.likeItem(clothesItem, liked)
                .subscribe(response -> {
                    LikedClothesItem item = response.getResponse();
                    if (item != null) {
                        emitter.onSuccess(new ClotheInfo<>(item));
                    } else {
                        emitter.onSuccess(new ClotheInfo(response.getError()));
                    }
                }, emitter::onError));
    }

    @Override
    public Single<Boolean> returnItemFromViewed(int clotheId) {
        return Single.create(emitter -> webLoader.returnItemFromViewed(clotheId)
                .subscribe(response -> {
                    emitter.onSuccess(response.isSuccessful());
                }, error -> {
                    Timber.e(error);
                    emitter.onError(error);
                }));
    }

    @Override
    public Single<List<ClotheInfo>> getClotheList() {
        int page = 1;
        return Single.create(emitter -> webLoader.getClothesPage(page)
                .subscribe(response -> {
                    ClothesPage clothePage = response.getResponse();
                    List<ClotheInfo> clotheInfoList = new LinkedList<>();
                    if (clothePage != null) {
                        List<ClothesItem> clothesItemList = clothePage.getItems();
                        if (clothesItemList.isEmpty()) {
                            ClotheInfo clotheInfo = new ClotheInfo(new UserException(
                                    App.getInstance().getString(R.string.end_of_not_viewed_list)));
                            clotheInfoList.add(clotheInfo);
                            emitter.onSuccess(clotheInfoList);
                        } else {
                            for (ClothesItem item : clothesItemList) {
                                item.getPics().get(0).downloadPic();
                                clotheInfoList.add(new ClotheInfo<ClothesItem>(item));
                            }
                        }
                    } else {
                        UserException error = ErrorRepository.makeError(response.getError());
                        clotheInfoList.add(new ClotheInfo(error));
                    }
                    emitter.onSuccess(clotheInfoList);
                }, emitter::onError));
    }


    @Override
    public Single<SparseArray<ClotheSize>> getSizes() {
        return Single.create(emitter -> webLoader.getSizes()
                .subscribe(sizesOkResponse -> {
                    List<ClotheSize> clotheSizes = sizesOkResponse.getResponse();
                    if (clotheSizes != null) {
                        SparseArray<ClotheSize> sparseArray = new SparseArray<>();
                        for (ClotheSize size : clotheSizes) {
                            sparseArray.put(size.getId(), size);
                        }
                        emitter.onSuccess(sparseArray);
                    } else {
                        UserException error = ErrorRepository.makeError(sizesOkResponse.getError());
                        emitter.onError(error);
                    }
                }, emitter::onError));
    }

    @Override
    public ClotheSizeType getSettingTopClothesSizeType() {
        return storage.getTopSizeType();
    }

    @Override
    public ClotheSizeType getSettingsBottomClothesSizeType() {
        return storage.getBottomSizeType();
    }

    @Override
    public void setSettingsTopClothesSizeType(ClotheSizeType clothesSizeType) {
        storage.setTopSizeType(clothesSizeType);
    }

    @Override
    public void setSettingsBottomClothesSizeType(ClotheSizeType clothesSizeType) {
        storage.setBottomSizeType(clothesSizeType);
    }

    @SuppressLint("CheckResult")
    @Override
    public void updateClotheBrandList() {
        webLoader.getBrandList()
                .subscribe(response -> {
                    brandsDao.upsert(response.getResponse());
                }, Timber::e);
    }

    @Override
    public LiveData<List<RoomBrand>> getBrandNames() {
        return brandsDao.getBrandsLiveData();
    }

    @SuppressLint("CheckResult")
    @Override
    public void updateClotheColorList() {
        webLoader.getColorList()
                .subscribe(response -> {
                    colorsDao.upsert(response.getResponse());
                }, Timber::e);
    }

    @Override
    public LiveData<List<RoomColor>> getClotheColors() {
        return colorsDao.getColorsLiveData();
    }

    @SuppressLint("CheckResult")
    @Override
    public void updateProductNameList() {
        webLoader.getProductNameList()
                .subscribe(response -> {
                    productNamesDao.upsert(response.getResponse());
                }, Timber::e);
    }

    @Override
    public LiveData<List<RoomProductName>> getClotheProductName() {
        return productNamesDao.getProductNamesLiveData();
    }

    @Override
    public void updateProductName(FilterProductName filterProductName) {
        Completable.create(emitter -> {
            productNamesDao.update(new RoomProductName(filterProductName, false));
            emitter.onComplete();
        })
                .subscribeOn(workThread)
                .subscribe();
    }

    @Override
    public void updateClotheBrand(FilterBrand filterBrand) {
        Completable.create(emitter -> {
            brandsDao.update(new RoomBrand(filterBrand, false));
            emitter.onComplete();
        })
                .subscribeOn(workThread)
                .subscribe();
    }

    @Override
    public void updateClotheColor(FilterColor filterColor) {
        Completable.create(emitter -> {
            colorsDao.update(new RoomColor(filterColor, false));
            emitter.onComplete();
        })
                .subscribeOn(workThread)
                .subscribe();
    }

    @Override
    public void resetCheckedFilters() {
        Completable.create(emitter -> {
            resetProductNameFilters();
            resetBrandFilters();
            resetColorFilters();
        })
                .subscribeOn(workThread)
                .subscribe();
    }

    @SuppressLint("CheckResult")
    @Override
    public Single<Boolean> isFiltersChecked() {
        return Single.create(emitter -> {
            productNamesDao.getCheckedFilters().subscribeOn(workThread).subscribe(roomProductNames -> {
                if (!roomProductNames.isEmpty()) {
                    emitter.onSuccess(true);
                } else {
                    brandsDao.getCheckedFilters().subscribe(roomBrands -> {
                        if (!roomBrands.isEmpty()) {
                            emitter.onSuccess(true);
                        } else {
                            colorsDao.getCheckedColors().subscribe(roomColors -> {
                                if (!roomColors.isEmpty()) {
                                    emitter.onSuccess(true);
                                } else {
                                    emitter.onSuccess(false);
                                }
                            });
                        }
                    });
                }
            });
        });
    }

    @Override
    public Boolean getIsNeedShowSizeDialogTop() {
        return storage.getIsNeedShowSizeDialogForRateItemsTop();
    }

    @Override
    public void setIsNeedShowSizeDialogTop(Boolean flag) {
        storage.setIsNeedShowSizeDialogForRateItemsTop(flag);
    }

    @Override
    public Boolean getIsNeedShowSizeDialogBottom() {
        return storage.getIsNeedShowSizeDialogForRateItemsBottom();
    }

    @Override
    public void setIsNeedShowSizeDialogBottom(Boolean flag) {
        storage.setIsNeedShowSizeDialogForRateItemsBottom(flag);
    }

    private void resetColorFilters() {
        colorsDao.resetColorFilters();
    }

    private void resetBrandFilters() {
        brandsDao.resetBrandFilters();
    }

    private void resetProductNameFilters() {
        productNamesDao.resetProductNameFilters();
    }
}
