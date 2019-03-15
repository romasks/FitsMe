package ru.fitsme.android.domain.interactors.favourites;

import android.support.annotation.NonNull;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import io.reactivex.Completable;
import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.SingleOnSubscribe;
import ru.fitsme.android.data.repositories.clothes.entity.ClothesPage;
import ru.fitsme.android.domain.boundaries.favourites.IFavouritesRepository;
import ru.fitsme.android.domain.boundaries.signinup.IUserInfoRepository;
import ru.fitsme.android.domain.entities.clothes.ClothesItem;
import ru.fitsme.android.domain.entities.exceptions.AppException;

@Singleton
public class FavouritesInteractor implements IFavouritesInteractor {

    private static final String TAG = "FavouritesInteractor";

    private final IFavouritesRepository favouritesRepository;
    private final IUserInfoRepository userInfoRepository;
    private final Scheduler workThread;
    private final Scheduler mainThread;

    @Inject
    FavouritesInteractor(IFavouritesRepository favouritesRepository,
                         IUserInfoRepository userInfoRepository,
                         @Named("work") Scheduler workThread,
                         @Named("main") Scheduler mainThread) {
        this.favouritesRepository = favouritesRepository;
        this.userInfoRepository = userInfoRepository;
        this.workThread = workThread;
        this.mainThread = mainThread;
    }

    @NonNull
    @Override
    public Single<Integer> getLastIndexSingle() {
        return Single.create((SingleOnSubscribe<Integer>) emitter ->
                emitter.onSuccess(0
//                        favouritesIndexRepository.getLastFavouritesItemIndex()
                ))
                .subscribeOn(workThread)
                .observeOn(mainThread);
    }

    @NonNull
    @Override
    public Single<ClothesItem> getSingleFavouritesItem(int index) {
        return Single.create((SingleOnSubscribe<ClothesItem>) emitter ->
                emitter.onSuccess(getFavouritesItem(index)))
                .subscribeOn(workThread)
                .observeOn(mainThread);
    }

    @NonNull
    @Override
    public Single<List<ClothesItem>> getSingleFavouritesPage(int page) {
        return Single.create((SingleOnSubscribe<List<ClothesItem>>) emitter ->
                emitter.onSuccess(getFavouritesPage(page)))
                .subscribeOn(workThread)
                .observeOn(mainThread);
    }

    @NonNull
    private List<ClothesItem> getFavouritesPage(int page) throws AppException {
        List<ClothesItem> items;
        String token = userInfoRepository.getAuthInfo().getToken();
        ClothesPage favouritesPage = favouritesRepository.getFavouritesPage(token, page);
        items = favouritesPage.getItems();
        return items;
    }

    private ClothesItem getFavouritesItem(int index) throws AppException {
        String token = userInfoRepository.getAuthInfo().getToken();
        return getFavouritesItem(token, index);
    }

    private ClothesItem getFavouritesItem(String token, int index) throws AppException {
        return favouritesRepository.getFavouritesItem(token, index);
    }

    @NonNull
    @Override
    public Completable removeItemFromFavourites(int index) {
        return Completable.create(emitter -> {
            String token = userInfoRepository.getAuthInfo().getToken();
            ClothesItem clothesItem = getFavouritesItem(token, index);
//            favouritesActionRepository.removeItem(token, clothesItem.getId());
            emitter.onComplete();
        })
                .subscribeOn(workThread)
                .observeOn(mainThread);
    }

    @NonNull
    @Override
    public Completable restoreItemToFavourites(int index) {
        return Completable.create(emitter -> {
            String token = userInfoRepository.getAuthInfo().getToken();
            ClothesItem clothesItem = getFavouritesItem(token, index);
//            favouritesActionRepository.restoreItem(token, clothesItem.getId());
            emitter.onComplete();
        })
                .subscribeOn(workThread)
                .observeOn(mainThread);
    }

    @NonNull
    @Override
    public Completable moveFavouritesItemToBasket(int index) {
        return Completable.create(emitter -> {
            String token = userInfoRepository.getAuthInfo().getToken();
            ClothesItem clothesItem = getFavouritesItem(token, index);
//            favouritesActionRepository.orderItem(token, clothesItem.getId());
            emitter.onComplete();
        })
                .subscribeOn(workThread)
                .observeOn(mainThread);
    }

}
