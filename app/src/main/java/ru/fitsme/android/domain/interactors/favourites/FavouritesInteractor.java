package ru.fitsme.android.domain.interactors.favourites;

import android.arch.lifecycle.LiveData;
import android.arch.paging.LivePagedListBuilder;
import android.arch.paging.PagedList;
import android.support.annotation.NonNull;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.concurrent.Executors;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import io.reactivex.Completable;
import io.reactivex.Scheduler;
import ru.fitsme.android.data.repositories.favourites.FavouritesDataSourceFactory;
import ru.fitsme.android.data.repositories.favourites.FavouritesRepository;
import ru.fitsme.android.domain.boundaries.favourites.IFavouritesActionRepository;
import ru.fitsme.android.domain.boundaries.favourites.IFavouritesRepository;
import ru.fitsme.android.domain.boundaries.signinup.IUserInfoRepository;
import ru.fitsme.android.domain.entities.clothes.ClothesItem;
import ru.fitsme.android.domain.entities.exceptions.AppException;
import ru.fitsme.android.domain.entities.exceptions.user.UserException;
import ru.fitsme.android.domain.entities.favourites.FavouritesItem;

@Singleton
public class FavouritesInteractor implements IFavouritesInteractor {

    private static final int PAGE_SIZE = 10;

    private final IFavouritesRepository favouritesRepository;
    private final IFavouritesActionRepository favouritesActionRepository;
    private final IUserInfoRepository userInfoRepository;
    private final Scheduler workThread;
    private final Scheduler mainThread;
    private final FavouritesDataSourceFactory favouritesDataSourceFactory;

    private LiveData<PagedList<FavouritesItem>> pagedListLiveData;
    private PagedList.Config config;

    @Inject
    FavouritesInteractor(IFavouritesRepository favouritesRepository,
                         IFavouritesActionRepository favouritesActionRepository,
                         IUserInfoRepository userInfoRepository,
                         FavouritesDataSourceFactory favouritesDataSourceFactory,
                         @Named("work") Scheduler workThread,
                         @Named("main") Scheduler mainThread) {
        this.favouritesRepository = favouritesRepository;
        this.favouritesActionRepository = favouritesActionRepository;
        this.userInfoRepository = userInfoRepository;
        this.favouritesDataSourceFactory = favouritesDataSourceFactory;
        this.workThread = workThread;
        this.mainThread = mainThread;

        config = new PagedList.Config.Builder()
                .setEnablePlaceholders(false)
                .setPageSize(PAGE_SIZE)
                .build();

        pagedListLiveData =
                new LivePagedListBuilder<>(this.favouritesDataSourceFactory, config)
                    .setFetchExecutor(Executors.newSingleThreadExecutor())
                    .build();
    }

    @Override
    public LiveData<PagedList<FavouritesItem>> getPagedListLiveData() {
        invalidateDataSource();
        return pagedListLiveData;
    }

    private void invalidateDataSource(){
        FavouritesRepository repository = favouritesDataSourceFactory.getSourceLiveData().getValue();
        if (repository != null) {
            Objects.requireNonNull(repository).invalidate();
        }
    }

    @NonNull
    @Override
    public Completable addFavouritesItemToCart(int position, int quantity) {
        return Completable.create(emitter -> {
            String token = userInfoRepository.getAuthInfo().getToken();
            PagedList<FavouritesItem> pagedList = pagedListLiveData.getValue();
            if (pagedList != null && pagedList.size() > position){
                FavouritesItem item = pagedList.get(position);
                if (item != null){
                    int clotheItemId = item.getItem().getId();
                    favouritesActionRepository.addItemToCart(token, clotheItemId, quantity);
                    invalidateDataSource();
                }
            }
            emitter.onComplete();
        })
                .subscribeOn(workThread)
                .observeOn(mainThread);
    }

    @NotNull
    @Override
    public Completable deleteFavouriteItem(Integer position) {
        return Completable.create(emitter -> {
            String token = userInfoRepository.getAuthInfo().getToken();
            PagedList<FavouritesItem> pagedList = pagedListLiveData.getValue();
            if (pagedList != null && pagedList.size() > position){
                FavouritesItem item = pagedList.get(position);
                if (item != null){
                    favouritesActionRepository.removeItem(token, item.getId());
                    invalidateDataSource();
                }
            }
            emitter.onComplete();
        })
                .subscribeOn(workThread)
                .observeOn(mainThread);
    }

//    private FavouritesItem getFavouritesItem(int index) throws AppException {
//        String token = userInfoRepository.getAuthInfo().getToken();
//        return getFavouritesItem(token, index);
//    }
//
//    private FavouritesItem getFavouritesItem(String token, int index) throws AppException {
//        return favouritesRepository.getFavouritesItem(token, index);
//    }


//    @NonNull
//    @Override
//    public Single<Integer> getLastIndexSingle() {
//        return Single.create((SingleOnSubscribe<Integer>) emitter ->
//                emitter.onSuccess(0
////                        favouritesIndexRepository.getLastFavouritesItemIndex()
//                ))
//                .subscribeOn(workThread)
//                .observeOn(mainThread);
//    }
//
//    @NonNull
//    @Override
//    public Single<FavouritesItem> getSingleFavouritesItem(int index) {
//        return Single.create((SingleOnSubscribe<FavouritesItem>) emitter ->
//                emitter.onSuccess(getFavouritesItem(index)))
//                .subscribeOn(workThread)
//                .observeOn(mainThread);
//    }
//
//    @NonNull
//    @Override
//    public Single<FavouritesPage> getSingleFavouritesPage(int page) {
//        return Single.create((SingleOnSubscribe<FavouritesPage>) emitter ->
//                emitter.onSuccess(getFavouritesPage(page)))
//                .subscribeOn(workThread)
//                .observeOn(mainThread);

//    }
//    @NonNull
//    private FavouritesPage getFavouritesPage(int page) throws AppException {
//        String token = userInfoRepository.getAuthInfo().getToken();

//        return favouritesRepository.getFavouritesPage(token, page);

//    }


//    @NonNull
//    @Override
//    public Completable restoreItemToFavourites(int index) {
//        return Completable.create(emitter -> {
//            FavouritesItem clothesItem = getFavouritesItem(index);
////            favouritesActionRepository.restoreItem(token, clothesItem.getId());
//            emitter.onComplete();
//        })
//                .subscribeOn(workThread)
//                .observeOn(mainThread);
//    }
//


}
