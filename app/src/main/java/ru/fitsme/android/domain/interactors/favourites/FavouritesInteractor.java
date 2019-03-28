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
import ru.fitsme.android.data.repositories.favourites.entity.FavouritesPage;
import ru.fitsme.android.domain.boundaries.favourites.IFavouritesActionRepository;
import ru.fitsme.android.domain.boundaries.favourites.IFavouritesRepository;
import ru.fitsme.android.domain.boundaries.signinup.IUserInfoRepository;
import ru.fitsme.android.domain.entities.exceptions.AppException;
import ru.fitsme.android.domain.entities.favourites.FavouritesItem;
import timber.log.Timber;

@Singleton
public class FavouritesInteractor implements IFavouritesInteractor {

    private static final String TAG = "FavouritesInteractor";

    private final IFavouritesRepository favouritesRepository;
    private final IFavouritesActionRepository favouritesActionRepository;
    private final IUserInfoRepository userInfoRepository;
    private final Scheduler workThread;
    private final Scheduler mainThread;

    @Inject
    FavouritesInteractor(IFavouritesRepository favouritesRepository,
                         IFavouritesActionRepository favouritesActionRepository,
                         IUserInfoRepository userInfoRepository,
                         @Named("work") Scheduler workThread,
                         @Named("main") Scheduler mainThread) {
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
                emitter.onSuccess(0
//                        favouritesIndexRepository.getLastFavouritesItemIndex()
                ))
                .subscribeOn(workThread)
                .observeOn(mainThread);
    }

    @NonNull
    @Override
    public Single<FavouritesItem> getSingleFavouritesItem(int index) {
        return Single.create((SingleOnSubscribe<FavouritesItem>) emitter ->
                emitter.onSuccess(getFavouritesItem(index)))
                .subscribeOn(workThread)
                .observeOn(mainThread);
    }

    @NonNull
    @Override
    public Single<FavouritesPage> getSingleFavouritesPage(int page) {
        return Single.create((SingleOnSubscribe<FavouritesPage>) emitter ->
                emitter.onSuccess(getFavouritesPage(page)))
                .subscribeOn(workThread)
                .observeOn(mainThread);
    }

    @NonNull
    private FavouritesPage getFavouritesPage(int page) throws AppException {
        String token = userInfoRepository.getAuthInfo().getToken();
        return favouritesRepository.getFavouritesPage(token, page);
    }

    private FavouritesItem getFavouritesItem(int index) throws AppException {
        String token = userInfoRepository.getAuthInfo().getToken();
        return getFavouritesItem(token, index);
    }

    private FavouritesItem getFavouritesItem(String token, int index) throws AppException {
        return favouritesRepository.getFavouritesItem(token, index);
    }

    @NonNull
    @Override
    public Completable removeItemFromFavourites(int index) {
        return Completable.create(emitter -> {
            FavouritesItem favouritesItem = getFavouritesItem(index);
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
            FavouritesItem clothesItem = getFavouritesItem(index);
//            favouritesActionRepository.restoreItem(token, clothesItem.getId());
            emitter.onComplete();
        })
                .subscribeOn(workThread)
                .observeOn(mainThread);
    }

    @NonNull
    @Override
    public Completable addFavouritesItemToCart(int index, int quantity) {
        return Completable.create(emitter -> {
            String token = userInfoRepository.getAuthInfo().getToken();
            FavouritesItem favouritesItem = getFavouritesItem(index);
            favouritesActionRepository.addItemToCart(token, favouritesItem.getItem().getId(), 0);
            emitter.onComplete();
        })
                .subscribeOn(workThread)
                .observeOn(mainThread);
    }

}
