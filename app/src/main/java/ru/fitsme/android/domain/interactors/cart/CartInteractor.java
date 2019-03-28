package ru.fitsme.android.domain.interactors.cart;

import android.support.annotation.NonNull;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.SingleOnSubscribe;
import ru.fitsme.android.domain.boundaries.favourites.IFavouritesRepository;
import ru.fitsme.android.domain.boundaries.signinup.IUserInfoRepository;
import ru.fitsme.android.domain.entities.exceptions.AppException;
import ru.fitsme.android.domain.entities.favourites.FavouritesItem;

@Singleton
public class CartInteractor implements ICartInteractor {

    private final String TAG = getClass().getName();

    private final IFavouritesRepository favouritesRepository;
    private final IUserInfoRepository userInfoRepository;
    private final Scheduler workThread;
    private final Scheduler mainThread;

    @Inject
    CartInteractor(IFavouritesRepository favouritesRepository,
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
    public Single<FavouritesItem> getSingleCartItem(int index) {
        return Single.create((SingleOnSubscribe<FavouritesItem>) emitter ->
                emitter.onSuccess(getFavouritesItem(index)))
                .subscribeOn(workThread)
                .observeOn(mainThread);
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
    public Single<List<FavouritesItem>> getSingleCartItems(int page) {
        return Single.create((SingleOnSubscribe<List<FavouritesItem>>) emitter ->
                emitter.onSuccess(getFavouritesItems(page)))
                .subscribeOn(workThread)
                .observeOn(mainThread);
    }

    @NonNull
    private List<FavouritesItem> getFavouritesItems(int page) throws AppException {
        String token = userInfoRepository.getAuthInfo().getToken();
        return favouritesRepository.getFavouritesPage(token, page).getItems();
    }
}
