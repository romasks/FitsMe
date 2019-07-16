package ru.fitsme.android.data.repositories.clothes;

import android.util.SparseArray;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;
import ru.fitsme.android.data.frameworks.retrofit.WebLoader;
import ru.fitsme.android.data.repositories.clothes.entity.ClothesPage;
import ru.fitsme.android.domain.boundaries.clothes.IClothesRepository;
import ru.fitsme.android.domain.entities.clothes.ClothesItem;
import ru.fitsme.android.domain.entities.clothes.LikedClothesItem;
import ru.fitsme.android.presentation.fragments.iteminfo.ClotheInfo;
import timber.log.Timber;

public class ClothesRepository implements IClothesRepository {

    private ClothesPage currentClothePage;
    private final WebLoader webLoader;

    @Inject
    public ClothesRepository(WebLoader webLoader) {
        this.webLoader = webLoader;
    }

    public Single<ClotheInfo> likeItem(ClothesItem clothesItem, boolean liked){
        return Single.create(emitter -> {
            webLoader.likeItem(clothesItem, liked)
                    .subscribe(likedClothesItemOkResponse -> {
                        LikedClothesItem item = likedClothesItemOkResponse.getResponse();
                        if (item != null) {
                            emitter.onSuccess(new ClotheInfo<LikedClothesItem>(item));
                        } else {
                            emitter.onSuccess(new ClotheInfo(likedClothesItemOkResponse.getError()));
                        }
                    });
        });
    }

    @Override
    public Single<List<ClotheInfo>> getClotheList() {
        int page = 1;
        return Single.create(emitter -> {
            webLoader.getClothesPage(page)
                    .subscribe(clothesPageOkResponse -> {
                        Timber.d(Thread.currentThread().getName());
                        currentClothePage = clothesPageOkResponse.getResponse();
                        List<ClotheInfo> clotheInfoList = new ArrayList<>();
                        if (currentClothePage != null){
                            List<ClothesItem> clothesItemList = currentClothePage.getItems();
                            for (int i = 0; i < clothesItemList.size(); i++) {
                                clotheInfoList.add(new ClotheInfo<ClothesItem>(clothesItemList.get(i)));
                            }
                        } else {
                            clotheInfoList.add(new ClotheInfo(clothesPageOkResponse.getError()));
                        }
                        emitter.onSuccess(clotheInfoList);
                    }, emitter::onError);
        });
    }


//    @NonNull
//    @Override
//    public ClothesItem getClothe(int index) throws AppException {
//        //return new ClothesItem();
//        int pageIndex = calculatePageIndex(index);
//        ClothesPage clothesPage = clothesPageSparseArray.get(pageIndex);
//        if (clothesPage == null) {
//            clothesPage = webLoader.getClothesPage(pageIndex + 1);
//            clothesPageSparseArray.put(pageIndex, clothesPage);
//        }
//        int itemIndex = calculateItemIndex(index);
//        return clothesPage.getItems().get(itemIndex);
//    }



//    private int calculateItemIndex(int index) {
//        return index % PAGE_SIZE;
//    }
//
//    private int calculatePageIndex(int index) {
//        return index / PAGE_SIZE;
//    }

//    private ClotheInfo extractLikedClotheItem(OkResponse<LikedClothesItem> likedClothesItemOkResponse){
//        LikedClothesItem likedClothesItem = likedClothesItemOkResponse.getResponse();
//        if (likedClothesItem != null){
//            return new ClotheInfo<LikedClothesItem>(likedClothesItem);
//        } else {
//            Error error = likedClothesItemOkResponse.getError();
//            UserException userException = makeError(error);
//            return new ClotheInfo(userException);
//        }
//    }
}
