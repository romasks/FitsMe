package ru.fitsme.android.domain.interactors.favourites;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Named;

import io.reactivex.Completable;
import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.SingleOnSubscribe;
import ru.fitsme.android.domain.boundaries.favourites.IFavouritesActionRepository;
import ru.fitsme.android.domain.boundaries.favourites.IFavouritesIndexRepository;
import ru.fitsme.android.domain.boundaries.favourites.IFavouritesRepository;
import ru.fitsme.android.domain.boundaries.signinup.IUserInfoRepository;
import ru.fitsme.android.domain.entities.clothes.ClothesItem;
import ru.fitsme.android.domain.entities.exceptions.AppException;

public class FavouritesInteractor implements IFavouritesInteractor {

    private final IFavouritesIndexRepository favouritesIndexRepository;
    private final IFavouritesRepository favouritesRepository;
    private final IFavouritesActionRepository favouritesActionRepository;
    private final IUserInfoRepository userInfoRepository;
    private final Scheduler workThread;
    private final Scheduler mainThread;

    public FavouritesInteractor(IFavouritesIndexRepository favouritesIndexRepository,
                                IFavouritesRepository favouritesRepository,
                                IFavouritesActionRepository favouritesActionRepository,
                                IUserInfoRepository userInfoRepository,
                                @Named("work") Scheduler workThread,
                                @Named("main") Scheduler mainThread) {
        this.favouritesIndexRepository = favouritesIndexRepository;
        this.favouritesRepository = favouritesRepository;
        this.favouritesActionRepository = favouritesActionRepository;
        this.userInfoRepository = userInfoRepository;
        this.workThread = workThread;
        this.mainThread = mainThread;
    }

    @NonNull
    @Override
    public Single<Integer> getLastIndexSingle() {
        return Single.create((SingleOnSubscribe<Integer>) emitter ->
                emitter.onSuccess(favouritesIndexRepository.getLastFavouritesItemIndex()))
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
    public Single<List<ClothesItem>> getSingleFavouritesItems(int firstIndex, int count) {
        return Single.create((SingleOnSubscribe<List<ClothesItem>>) emitter ->
                emitter.onSuccess(getFavouritesItems(firstIndex, count)))
                .subscribeOn(workThread)
                .observeOn(mainThread);
    }

    @NonNull
    private List<ClothesItem> getFavouritesItems(int firstIndex, int count) throws AppException {
        List<ClothesItem> items = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            items.add(getFavouritesItem(firstIndex + i));
        }
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
            favouritesActionRepository.removeItem(token, clothesItem.getId());
//            favouritesIndexRepository.setLastFavouritesItemIndex(index);
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
            favouritesActionRepository.restoreItem(token, clothesItem.getId());
//            favouritesIndexRepository.setLastFavouritesItemIndex(index + 1);
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
            favouritesActionRepository.orderItem(token, clothesItem.getId());
//            favouritesIndexRepository.setLastFavouritesItemIndex(index + 1);
            emitter.onComplete();
        })
                .subscribeOn(workThread)
                .observeOn(mainThread);
    }
}
