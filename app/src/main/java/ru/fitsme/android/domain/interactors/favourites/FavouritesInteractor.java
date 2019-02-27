package ru.fitsme.android.domain.interactors.favourites;

import android.support.annotation.NonNull;

import java.util.List;

import javax.inject.Named;

import io.reactivex.Completable;
import io.reactivex.Scheduler;
import io.reactivex.Single;
import ru.fitsme.android.domain.boundaries.favourites.IFavouritesActionRepository;
import ru.fitsme.android.domain.boundaries.favourites.IFavouritesIndexRepository;
import ru.fitsme.android.domain.boundaries.favourites.IFavouritesRepository;
import ru.fitsme.android.domain.boundaries.signinup.IUserInfoRepository;
import ru.fitsme.android.domain.entities.clothes.ClothesItem;

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
        return null;
    }

    @NonNull
    @Override
    public Single<ClothesItem> getSingleFavouritesItem(int index) {
        return null;
    }

    @NonNull
    @Override
    public Single<List<ClothesItem>> getSingleFavouritesItems(int firstIndex, int count) {
        return null;
    }

    @NonNull
    @Override
    public Completable removeItemFromFavourites(int index) {
        return null;
    }

    @NonNull
    @Override
    public Completable revertItemToFavourites(int index) {
        return null;
    }

    @NonNull
    @Override
    public Completable moveFavouritesItemToBasket(int index) {
        return null;
    }
}
