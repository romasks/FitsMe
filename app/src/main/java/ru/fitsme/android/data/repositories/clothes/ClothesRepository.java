package ru.fitsme.android.data.repositories.clothes;

import android.util.SparseArray;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;
import ru.fitsme.android.R;
import ru.fitsme.android.app.App;
import ru.fitsme.android.data.frameworks.retrofit.WebLoaderNetworkChecker;
import ru.fitsme.android.data.frameworks.sharedpreferences.ISettingsStorage;
import ru.fitsme.android.data.repositories.ErrorRepository;
import ru.fitsme.android.data.repositories.clothes.entity.ClotheSizeType;
import ru.fitsme.android.data.repositories.clothes.entity.ClothesPage;
import ru.fitsme.android.domain.boundaries.clothes.IClothesRepository;
import ru.fitsme.android.domain.entities.clothes.ClotheSize;
import ru.fitsme.android.domain.entities.clothes.ClothesItem;
import ru.fitsme.android.domain.entities.clothes.LikedClothesItem;
import ru.fitsme.android.domain.entities.exceptions.user.UserException;
import ru.fitsme.android.domain.entities.favourites.FavouritesItem;
import ru.fitsme.android.presentation.fragments.iteminfo.ClotheInfo;

public class ClothesRepository implements IClothesRepository {

    private final WebLoaderNetworkChecker webLoader;
    private final ISettingsStorage storage;

    @Inject
    ClothesRepository(WebLoaderNetworkChecker webLoader, ISettingsStorage storage) {
        this.webLoader = webLoader;
        this.storage = storage;
    }

    @Override
    public Single<ClotheInfo> likeItem(ClotheInfo clotheInfo, boolean liked){
        ClothesItem clothesItem;
        if (clotheInfo.getClothe() instanceof ClothesItem) {
           clothesItem = (ClothesItem) clotheInfo.getClothe();
        } else if (clotheInfo.getClothe() instanceof LikedClothesItem) {
            LikedClothesItem likedClothesItem = (LikedClothesItem) clotheInfo.getClothe();
            clothesItem = likedClothesItem.getClothe();
        } else {
            throw new TypeNotPresentException(clotheInfo.toString(), null);
        }
        return Single.create(emitter -> {
            webLoader.likeItem(clothesItem, liked)
                    .subscribe(likedClothesItemOkResponse -> {
                        LikedClothesItem item = likedClothesItemOkResponse.getResponse();
                        if (item != null) {
                            emitter.onSuccess(new ClotheInfo<LikedClothesItem>(item));
                        } else {
                            emitter.onSuccess(new ClotheInfo(likedClothesItemOkResponse.getError()));
                        }
                    }, emitter::onError);
        });
    }

    @Override
    public Single<List<ClotheInfo>> getClotheList() {
        int page = 1;
        return Single.create(emitter -> {
            webLoader.getClothesPage(page)
                    .subscribe(clothesPageOkResponse -> {
                        ClothesPage clothePage = clothesPageOkResponse.getResponse();
                        List<ClotheInfo> clotheInfoList = new LinkedList<>();
                        if (clothePage != null){
                            List<ClothesItem> clothesItemList = clothePage.getItems();
                            if (clothesItemList.size() == 0){
                                ClotheInfo clotheInfo = new ClotheInfo(new UserException(
                                        App.getInstance().getString(R.string.end_of_not_viewed_list)));
                                clotheInfoList.add(clotheInfo);
                                emitter.onSuccess(clotheInfoList);
                            } else {
                                for (int i = 0; i < clothesItemList.size(); i++) {
                                    clotheInfoList.add(new ClotheInfo<ClothesItem>(clothesItemList.get(i)));
                                }
                            }
                        } else {
                            UserException error = ErrorRepository.makeError(clothesPageOkResponse.getError());
                            clotheInfoList.add(new ClotheInfo(error));
                        }
                        emitter.onSuccess(clotheInfoList);
                    }, emitter::onError);
        });
    }


    @Override
    public Single<SparseArray<ClotheSize>> getSizes() {
        return Single.create(emitter -> webLoader.getSizes()
                .subscribe(sizesOkResponse -> {
                    List<ClotheSize> clotheSizes = sizesOkResponse.getResponse();
                    if (clotheSizes != null) {
                        SparseArray<ClotheSize> sparseArray = new SparseArray<>();
                        for (int i = 0; i < clotheSizes.size(); i++) {
                            sparseArray.put(clotheSizes.get(i).getId(), clotheSizes.get(i));
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
}
