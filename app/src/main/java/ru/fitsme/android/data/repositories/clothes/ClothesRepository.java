package ru.fitsme.android.data.repositories.clothes;

import android.util.SparseArray;

import java.util.LinkedList;
import java.util.List;

import javax.inject.Inject;

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
import ru.fitsme.android.data.frameworks.room.db.AppDatabase;
import ru.fitsme.android.data.frameworks.sharedpreferences.ISettingsStorage;
import ru.fitsme.android.data.repositories.ErrorRepository;
import ru.fitsme.android.data.repositories.clothes.entity.ClotheSizeType;
import ru.fitsme.android.data.repositories.clothes.entity.ClothesPage;
import ru.fitsme.android.domain.boundaries.clothes.IClothesRepository;
import ru.fitsme.android.domain.entities.clothes.ClotheBrand;
import ru.fitsme.android.domain.entities.clothes.ClotheColor;
import ru.fitsme.android.domain.entities.clothes.ClotheProductName;
import ru.fitsme.android.domain.entities.clothes.ClotheSize;
import ru.fitsme.android.domain.entities.clothes.ClothesItem;
import ru.fitsme.android.domain.entities.clothes.LikedClothesItem;
import ru.fitsme.android.domain.entities.exceptions.user.UserException;
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
                        if (clothePage != null) {
                            List<ClothesItem> clothesItemList = clothePage.getItems();
                            if (clothesItemList.size() == 0) {
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

    @Override
    public void updateClotheBrands(){
        webLoader.getBrandList()
            .subscribe(listOkResponse -> {
                List<ClotheBrand> clotheBrands = listOkResponse.getResponse();
                if (clotheBrands != null){
                    BrandsDao brandsDao = AppDatabase.getInstance().getBrandsDao();
                        brandsDao.getBrands()
                            .subscribe(roomBrands -> {
                                for (int i = 0; i < clotheBrands.size(); i++) {
                                    ClotheBrand brand = clotheBrands.get(i);
                                    for (int j = 0; j < roomBrands.size(); j++) {
                                        RoomBrand roomBrand = roomBrands.get(j);
                                        if (brand.getId() == roomBrand.getId()){
                                            brandsDao.update(new RoomBrand(roomBrand.getId(), roomBrand.getTitle(), roomBrand.isChecked(), true));
                                        } else {
                                            brandsDao.insert(new RoomBrand(brand.getId(), brand.getTitle(), false, false));
                                        }
                                    }
                                }
                                brandsDao.clearNotUpdatedBrands();
                                setRoomBrandsNotUpdated();
                            });
                }
            });
    }

    private void setRoomBrandsNotUpdated() {
        BrandsDao brandsDao = AppDatabase.getInstance().getBrandsDao();
        brandsDao.getBrands()
            .subscribe(roomBrandList -> {
                for (int i = 0; i < roomBrandList.size(); i++) {
                    RoomBrand brand = roomBrandList.get(i);
                    brandsDao.update(new RoomBrand(brand.getId(), brand.getTitle(), brand.isChecked(), false));
                }
            });
    }

    @Override
    public void updateClotheColors(){
        webLoader.getColorList()
                .subscribe(listOkResponse -> {
                    List<ClotheColor> clotheColorList = listOkResponse.getResponse();
                    if (clotheColorList != null){
                        ColorsDao colorsDao = AppDatabase.getInstance().getColorsDao();
                        colorsDao.getColors()
                                .subscribe(roomColors -> {
                                    for (int i = 0; i < clotheColorList.size(); i++) {
                                        ClotheColor color = clotheColorList.get(i);
                                        for (int j = 0; j < roomColors.size(); j++) {
                                            RoomColor roomColor = roomColors.get(j);
                                            if (color.getId() == roomColor.getId()){
                                                colorsDao.update(new RoomColor(roomColor.getId(), roomColor.getColorName(), roomColor.isChecked(), true));
                                            } else {
                                                colorsDao.insert(new RoomColor(color.getId(), color.getColorName(), false, false));
                                            }
                                        }
                                    }
                                    colorsDao.clearNotUpdatedColors();
                                    setRoomColorsNotUpdated();
                                });
                    }
                });
    }

    private void setRoomColorsNotUpdated() {
        ColorsDao colorsDao = AppDatabase.getInstance().getColorsDao();
        colorsDao.getColors()
                .subscribe(roomColorsList -> {
                    for (int i = 0; i < roomColorsList.size(); i++) {
                        RoomColor roomColor = roomColorsList.get(i);
                        colorsDao.update(new RoomColor(roomColor.getId(), roomColor.getColorName(), roomColor.isChecked(), false));
                    }
                });
    }

    @Override
    public void updateProductNamesColors(){
        webLoader.getProductNameList()
                .subscribe(listOkResponse -> {
                    List<ClotheProductName> clotheProductNameList = listOkResponse.getResponse();
                    if (clotheProductNameList != null){
                        ProductNamesDao productNamesDao = AppDatabase.getInstance().getProductNamesDao();
                        productNamesDao.getProductNames()
                                .subscribe(roomProductNames -> {
                                    for (int i = 0; i < clotheProductNameList.size(); i++) {
                                        ClotheProductName productName = clotheProductNameList.get(i);
                                        for (int j = 0; j < roomProductNames.size(); j++) {
                                            RoomProductName roomProductName = roomProductNames.get(j);
                                            if (productName.getId() == roomProductName.getId()){
                                                productNamesDao.update(new RoomProductName(
                                                        roomProductName.getId(), roomProductName.getTitle(), roomProductName.getType(), roomProductName.isChecked(), true));
                                            } else {
                                                productNamesDao.insert(new RoomProductName(productName.getId(), productName.getTitle(), productName.getType(), false, false));
                                            }
                                        }
                                    }
                                    productNamesDao.clearNotUpdatedProductNames();
                                    setRoomProductNamesNotUpdated();
                                });
                    }
                });
    }

    private void setRoomProductNamesNotUpdated() {
        ProductNamesDao productNamesDao = AppDatabase.getInstance().getProductNamesDao();
        productNamesDao.getProductNames()
                .subscribe(roomProductNames -> {
                    for (int i = 0; i < roomProductNames.size(); i++) {
                        RoomProductName roomProductName = roomProductNames.get(i);
                        productNamesDao.update(new RoomProductName(roomProductName.getId(), roomProductName.getTitle(), roomProductName.getType(), roomProductName.isChecked(), false));
                    }
                });
    }
}
